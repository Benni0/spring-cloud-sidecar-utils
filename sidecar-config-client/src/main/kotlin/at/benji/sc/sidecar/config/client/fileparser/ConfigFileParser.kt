package at.benji.sc.sidecar.config.client.fileparser

import java.util.*

/**
 * Interface for ConfigFile parsing, parameter modifying and (over)writing
 *
 * the getValue and setValue methods must be based on the type of the configuration file
 */
interface ConfigFileParser {

    /**
     * Reads the configuration from file, [filePath] must be the absolute path
     */
    fun readFromFile(filePath: String)

    /**
     * (Over)writes the configuration file with the modified configuration parameters
     * [filePath] must be the absolute path
     * If the file doesn't exist, a new file should be created
     */
    fun writeToFile(filePath: String)

    /**
     * Adds or replaces the value of the given [param] parameter with the given [value]
     */
    fun setValue(param: String, value: String)

    /**
     * @return the value of the given [param] from the current (modified) configuration state
     */
    fun getValue(param: String): Optional<String>

    /**
     * @return the size (eg. parameters or line count) of the configuration
     * for testing proposes only
     */
    val parameterCount: Int

}