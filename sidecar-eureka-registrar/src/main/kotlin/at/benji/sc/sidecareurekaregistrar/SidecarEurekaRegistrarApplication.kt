package at.benji.sc.sidecareurekaregistrar

import at.benji.sc.sidecareurekaregistrar.boot.EnableSidecarEurekaRegistrar
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSidecarEurekaRegistrar
class SidecarEurekaRegistrarApplication

fun main(args: Array<String>) {
    runApplication<SidecarEurekaRegistrarApplication>(*args)
}
