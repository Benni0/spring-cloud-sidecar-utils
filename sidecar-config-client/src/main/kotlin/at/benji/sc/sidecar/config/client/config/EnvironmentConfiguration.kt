package at.benji.sc.sidecar.config.client.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class EnvironmentConfiguration {

    @Autowired
    lateinit var system: SystemVariables

    @Autowired
    lateinit var config: ConfigurationFiles
}