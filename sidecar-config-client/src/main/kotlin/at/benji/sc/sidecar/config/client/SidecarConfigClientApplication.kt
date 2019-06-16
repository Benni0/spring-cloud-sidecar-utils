package at.benji.sc.sidecar.config.client

import at.benji.sc.sidecar.config.client.boot.EnableSidecarConfigClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
@SpringBootApplication
@EnableSidecarConfigClient
class SidecarConfigClientApplication

fun main(args: Array<String>) {
    runApplication<SidecarConfigClientApplication>(*args)
}