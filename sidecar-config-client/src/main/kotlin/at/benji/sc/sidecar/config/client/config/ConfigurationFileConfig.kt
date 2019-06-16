package at.benji.sc.sidecar.config.client.config

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

@Validated
class ConfigurationFileConfig {
    @field:[NotBlank NotEmpty]
    lateinit var file: String
    lateinit var action: Action
    lateinit var parameters: List<@Pattern(regexp = "^[^=][^=]*=[^=]*\$") String>
    lateinit var type: FileType

    fun getParams(): Map<String, String> {
        val varMap = HashMap<String, String>()

        for(variable in parameters) {
            val arr = variable.split('=')
            if(arr.size != 2)
                throw MalformedConfigException(variable)

            varMap.put(arr[0], arr[1])
        }
        return varMap
    }
}

enum class Action { CREATE, MODIFY, OVERWRITE, REMOVE}
enum class FileType { PROPERTIES, DOVECOT, JSON, XML, YAML }