package at.benji.sc.sidecareurekaregistrar.sidecar

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Value("\${sidecar.check.command}")
    private lateinit var checkCommand: String

    @Value("\${sidecar.check.result}")
    private lateinit var expectedResult: String

    @Bean
    fun healthCheck(): SidecarHealthIndicator {
        return CommandExecHealthCheck(checkCommand, expectedResult)
    }
}