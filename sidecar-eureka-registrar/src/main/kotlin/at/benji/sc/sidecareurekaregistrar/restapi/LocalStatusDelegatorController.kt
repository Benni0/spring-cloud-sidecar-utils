package at.benji.sc.sidecareurekaregistrar.restapi

import at.benji.sc.sidecareurekaregistrar.sidecar.SidecarHealthIndicator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.Health
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LocalStatusDelegatorController {

    @Autowired
    private lateinit var healthIndicator: SidecarHealthIndicator

    @RequestMapping("/delegating-status")
    fun sidecarHealthStatus(): Health {
        return healthIndicator.health()
    }
}