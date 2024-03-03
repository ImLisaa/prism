package net.phantara.prism.server.api.json

import com.google.gson.*
import java.io.IOException
import java.io.Reader
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 13:54
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class Document {
    private var jsonObject = JsonObject()

    constructor(path: Path) {
        this.read(path)
    }

    constructor(reader: Reader) {
        this.read(reader)
    }

    @JvmOverloads
    constructor(jsonObject: JsonObject = JsonObject()) {
        this.jsonObject = jsonObject
    }

    constructor(json: String) {
        this.jsonObject = GSON.fromJson(json, JsonObject::class.java)
    }

    constructor(any: Any) {
        this.setJsonObject(any)
    }

    fun <T> get(key: String, clazz: Class<T>): T {
        return GSON.fromJson(jsonObject[key], clazz)
    }

    fun <T> getUnformatted(key: String, clazz: Class<T>): T {
        return GSON.fromJson(JsonParser.parseString(jsonObject[key].toString()), clazz)
    }

    fun <T> get(key: String, type: Type): T {
        return GSON.fromJson(jsonObject[key], type)
    }

    fun addIfNotExists(key: String, state: Boolean): Document {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, state: Number): Document {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, state: String): Document {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, state: Any): Document {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, array: JsonArray): Document {
        if (!has(key)) set(key, array)
        return this
    }

    fun addIfNotExists(key: String, element: JsonElement): Document {
        if (!has(key)) set(key, element)
        return this
    }

    fun <T> get(clazz: Class<T>): T {
        return GSON.fromJson(this.jsonObject, clazz)
    }

    fun <T> get(type: Type): T {
        return GSON.fromJson(this.jsonObject, type)
    }

    fun set(key: String, any: Any): Document {
        jsonObject.add(key, GSON.toJsonTree(any, any.javaClass))
        return this
    }

    fun read(path: Path): Document {
        if (!Files.exists(path)) return this
        try {
            Files.newBufferedReader(path).use { fileReader ->
                this.jsonObject =
                    JsonParser.parseReader(fileReader).asJsonObject
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return this
    }

    fun has(key: String): Boolean {
        return jsonObject.has(key)
    }

    fun read(reader: Reader): Document {
        this.jsonObject = JsonParser.parseReader(reader).asJsonObject
        return this
    }

    fun write(path: Path): Document {
        try {
            Files.newBufferedWriter(path).use { writer ->
                writer.write(GSON.toJson(this.jsonObject))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    fun setJsonObject(jsonObject: JsonObject): Document {
        this.jsonObject = jsonObject
        return this
    }

    fun setJsonObject(any: Any): Document {
        this.jsonObject = GSON.toJsonTree(any).asJsonObject
        return this
    }

    fun getJsonObject(): JsonObject {
        return this.jsonObject
    }

    override fun toString(): String {
        return jsonObject.toString()
    }

    companion object {
        private val GSON: Gson = GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()
    }
}