package at.benji.sc.sidecar.config.client.boot

import org.springframework.context.annotation.Import
import java.lang.annotation.Inherited

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@Import(SidecarConfigClientConfiguration::class)
annotation class EnableSidecarConfigClient