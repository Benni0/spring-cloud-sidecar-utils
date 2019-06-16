package at.benji.sc.sidecareurekaregistrar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.sidecar.EnableSidecar

@SpringBootApplication
@EnableSidecar
class SidecarEurekaRegistrarApplication

fun main(args: Array<String>) {
    runApplication<SidecarEurekaRegistrarApplication>(*args)
}
