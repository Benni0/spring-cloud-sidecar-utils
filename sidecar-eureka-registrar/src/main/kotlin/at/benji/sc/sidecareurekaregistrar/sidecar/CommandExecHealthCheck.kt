package at.benji.sc.sidecareurekaregistrar.sidecar

import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.health.Health
import java.io.IOException
import java.util.concurrent.TimeUnit

class CommandExecHealthCheck(
        val command: String,
        val expected: String) : SidecarHealthIndicator {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun health(): Health {
        var result: Health.Builder

        try{
            val output = executeCommand(command)
            logger.info("Check Output: $output")

            if(output.contains(expected)) {
                result = Health.up()
            } else {
                result = Health.down().withDetail("reason", output)
            }
        } catch (ex: IOException) {
            logger.warn("Failed to execute command.", ex)
            result = Health.down().withException(ex)
        }
        return result.build()
    }

    fun executeCommand(command: String, timeout: Long = 3): String {
        val process = Runtime.getRuntime().exec(command.split(' ').toTypedArray())
        process.waitFor(timeout, TimeUnit.SECONDS)
        return process.inputStream.bufferedReader().readText()
    }

}