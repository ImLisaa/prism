package net.phantara.prism.server.api.properties

import net.phantara.prism.server.api.json.Document
import net.phantara.prism.server.api.properties.defaults.DefaultPrismServerProperties
import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 14:09
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class PrismServerProperties {

    private var document: Document
    private var path: Path

    init {
        val loadedDocument: Document
        this.path = Path.of("prism-properties.json")
        if (Files.notExists(this.path)) {
            loadedDocument = DefaultPrismServerProperties.get()
            loadedDocument.write(this.path)
        } else {
            loadedDocument = Document(this.path)
        }
        this.document = loadedDocument
    }

    fun <T> getProperty(property: String, clazz: Class<T>): T {
        return document.get(property, clazz)
    }

    fun setProperty(property: String, value: Any) {
        document.set(property, value)
    }

    fun updateDocument() {
        document.write(this.path)
    }

    fun reload() {
        document.read(this.path)
    }
}