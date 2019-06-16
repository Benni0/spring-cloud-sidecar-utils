package at.benji.sc.sidecar.config.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import java.lang.IndexOutOfBoundsException
import javax.validation.constraints.Pattern

@Configuration
@ConfigurationProperties("system")
@Validated
class SystemVariables {
    lateinit var variables: List<@Pattern(regexp = "^[^=][^=]*=[^=]*\$") String>

    fun getVars(): Map<String, String> {
        val varMap = HashMap<String, String>()

        for(variable in variables) {
            val arr = variable.split('=')
            if(arr.size != 2)
                throw MalformedConfigException(variable)

            varMap.put(arr[0], arr[1])
        }
        return varMap
    }
}