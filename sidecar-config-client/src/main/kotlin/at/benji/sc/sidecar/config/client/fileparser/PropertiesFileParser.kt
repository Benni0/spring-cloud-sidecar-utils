package at.benji.sc.sidecar.config.client.fileparser

import java.io.File
import java.util.*

class PropertiesFileParser(
        var delemiter: Char = '=',
        var commentPrefix: String = "#"
): ConfigFileParser {
    private val config: MutableList<MutablePair> = mutableListOf()

    override fun readFromFile(filePath: String) {
        val file = File(filePath)
        if(!file.canRead())
            throw FileSystemException(file = file, reason = "Unable to read from file")

        for(line in file.readLines()) {
            val confLine = line.split("=")
            if(confLine.size == 2) {
                config.add(MutablePair(confLine[0], confLine[1]))
            } else {
                config.add(MutablePair(line, ""))
            }
        }
    }

    override fun setValue(param: String, value: String) {
        var parameter = config.stream().filter { t -> t.key == param } .findAny()

        if(!parameter.isPresent) {
            parameter = config.stream().filter { t -> t.key == "$commentPrefix$param" || t.key == "$commentPrefix $param" } .findAny()
            if(parameter.isPresent)
                parameter.get().key = param
        }

        if(parameter.isPresent) {
            parameter.get().value = value
        } else {
            config.add(MutablePair(param, value))
        }
    }

    override fun writeToFile(filePath: String) {
        val file = File(filePath)
        if(!file.exists())
            file.createNewFile()
        if(!file.canWrite()) {
            throw FileSystemException(file = file, reason = "Unable to write to file")
        }

        file.printWriter().use { out ->
           for(param in config) {
               if(param.value.trim() != "" && param.key.trim() != "") {
                   out.println("${param.key}$delemiter${param.value}")
               } else {
                   out.println("${param.key}${param.value}")
               }
           }
        }
    }

    override fun getValue(param: String): Optional<String> {
        val parameter = config.stream().filter { t -> t.key == param } .findAny()
        if(parameter.isPresent)
            return Optional.of(parameter.get().value)

        return Optional.empty()
    }

    override val parameterCount: Int
        get() = config.size
}

data class MutablePair(
        var key: String,
        var value: String
)