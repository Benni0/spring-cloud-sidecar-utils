package at.benji.sc.sidecar.config.client.boot

import at.benji.sc.sidecar.config.client.config.Action
import at.benji.sc.sidecar.config.client.config.EnvironmentConfiguration
import at.benji.sc.sidecar.config.client.config.MalformedConfigException
import at.benji.sc.sidecar.config.client.fileparser.ConfigFileParserFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import java.io.File
import java.io.FileNotFoundException
import javax.annotation.PostConstruct
import javax.xml.validation.Validator

class SidecarConfigClientConfiguration {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var config: EnvironmentConfiguration

    @Value("\${spring.application.name}")
    private lateinit var appName: String

    @Value("\${spring.profiles.active}")
    private lateinit var profile: String

    @Value("\${spring.cloud.config.label:master}")
    private lateinit var label: String


    @PostConstruct
    fun doEnvironmentConfiguration() {
        logger.info("Application Name: $appName")
        logger.info("Profile: $profile")
        logger.info("Label: $label")

        try {
            handleEnvironmentVariables()
            handleConfigFiles()
        }catch (ex: MalformedConfigException) {
            logger.error("Unable to process config parameter: ${ex.message}")
        }
    }

    fun handleEnvironmentVariables() {
        logger.info("Handle Environment Variables started")
        for(variable in config.system.getVars()) {
            logger.info("Set Variable ${variable.key}")
            Runtime.getRuntime().exec(arrayOf("bash", "-c", "echo 'export ${variable.key}=${variable.value}' >> /tmp/environment.sh"))
        }
        logger.info("Handle Environment Variables finished")
    }

    fun handleConfigFiles() {
        logger.info("Handle Config Files started")
        for(file in config.config.files) {
            var filePath = file.file
            if(!File(filePath).isAbsolute)
                filePath = "${config.config.basePath}${File.separator}${file.file}"


            logger.info("Processing ${filePath}")
            val configFileParser = ConfigFileParserFactory.getConfigFileParser(file.type)

            when(file.action) {
                Action.MODIFY -> {
                    try {
                        configFileParser.readFromFile(filePath)
                        for (param in file.getParams()) {
                            logger.debug("Set Param ${param.key}")
                            configFileParser.setValue(param.key, param.value)
                        }
                        configFileParser.writeToFile(filePath)
                    }catch (ex: FileNotFoundException) {
                        logger.error("Unable to modify File: $filePath; File Not Found")
                    }
                }
                Action.REMOVE -> {
                    val configFile = File(filePath)
                    configFile.delete()
                }
                Action.OVERWRITE -> {
                    val configFile = File(filePath)
                    configFile.delete()
                    for(param in file.getParams()) {
                        logger.debug("Set Param ${param.key}")
                        configFileParser.setValue(param.key, param.value)
                    }
                    configFileParser.writeToFile(filePath)
                }
                Action.CREATE -> {
                    val configFile = File(filePath)
                    configFile.delete()
                    for(param in file.getParams()) {
                        logger.debug("Set Param ${param.key}")
                        configFileParser.setValue(param.key, param.value)
                    }
                    configFileParser.writeToFile(filePath)
                }

            }
        }
        logger.info("Handle Config Files finished")
    }
}