package at.benji.sc.sidecar.config.client.fileparser

import at.benji.sc.sidecar.config.client.config.FileType

object ConfigFileParserFactory {
    fun getConfigFileParser(type: FileType): PropertiesFileParser {
        when(type) {
            FileType.PROPERTIES ->
                return PropertiesFileParser()
            else ->
                throw Exception("Unsupported File Type: $type")
        }
    }
}