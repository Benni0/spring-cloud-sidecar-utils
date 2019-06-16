package at.benji.sc.sidecar.config.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("config")
@EnableConfigurationProperties
class ConfigurationFiles {
    var basePath: String = "/"

    lateinit var files: List<ConfigurationFileConfig>

}