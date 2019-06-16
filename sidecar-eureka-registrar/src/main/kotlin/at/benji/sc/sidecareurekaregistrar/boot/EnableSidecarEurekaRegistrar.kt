package at.benji.sc.sidecareurekaregistrar.boot

import at.benji.sc.sidecareurekaregistrar.restapi.LocalStatusDelegatorController
import org.springframework.cloud.netflix.sidecar.EnableSidecar
import org.springframework.context.annotation.Import
import java.lang.annotation.Inherited

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@Import(LocalStatusDelegatorController::class)
@EnableSidecar
annotation class EnableSidecarEurekaRegistrar