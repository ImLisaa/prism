package net.phantara.prism.server.api.properties.defaults

import net.phantara.prism.server.api.json.Document

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 14:14
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class DefaultPrismServerProperties {

    companion object {
        @JvmStatic
        fun get(): Document {
            return Document()
                .addIfNotExists("address", "0.0.0.0")
                .addIfNotExists("port", 25565)
                .addIfNotExists("player-limit", 20)
                .addIfNotExists("motd", "A Prism Server Instance")
                .addIfNotExists("online-mode", true)
                .addIfNotExists("difficulty", "EASY")
                .addIfNotExists("bungeecord-support", false)
                .addIfNotExists("velocity-support", false)
                .addIfNotExists("velocity-secret-key", "YOUR-VELOCITY-SECRET-KEY")
        }
    }
}