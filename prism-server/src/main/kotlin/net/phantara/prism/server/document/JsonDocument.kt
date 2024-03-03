package net.phantara.prism.server.JsonDocument

import com.google.gson.*
import java.io.IOException
import java.io.Reader
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:44
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class JsonDocument {
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

    constructor(`object`: Any) {
        this.setJsonObject(`object`)
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

    fun addIfNotExists(key: String, state: Boolean): JsonDocument {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, state: Number): JsonDocument {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, state: String): JsonDocument {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, state: Any): JsonDocument {
        if (!has(key)) set(key, state)
        return this
    }

    fun addIfNotExists(key: String, array: JsonArray): JsonDocument {
        if (!has(key)) set(key, array)
        return this
    }

    fun addIfNotExists(key: String, element: JsonElement): JsonDocument {
        if (!has(key)) set(key, element)
        return this
    }

    fun <T> get(clazz: Class<T>): T {
        return GSON.fromJson(this.jsonObject, clazz)
    }

    fun <T> get(type: Type): T {
        return GSON.fromJson(this.jsonObject, type)
    }

    fun set(key: String?, `object`: Any): JsonDocument {
        jsonObject.add(key, GSON.toJsonTree(`object`, `object`.javaClass))
        return this
    }

    fun read(path: Path): JsonDocument {
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

    fun read(reader: Reader): JsonDocument {
        this.jsonObject = JsonParser.parseReader(reader).asJsonObject
        return this
    }

    fun write(path: Path): JsonDocument {
        try {
            Files.newBufferedWriter(path).use { writer ->
                writer.write(GSON.toJson(this.jsonObject))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    fun setJsonObject(jsonObject: JsonObject): JsonDocument {
        this.jsonObject = jsonObject
        return this
    }

    fun setJsonObject(`object`: Any): JsonDocument {
        this.jsonObject = GSON.toJsonTree(`object`).asJsonObject
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