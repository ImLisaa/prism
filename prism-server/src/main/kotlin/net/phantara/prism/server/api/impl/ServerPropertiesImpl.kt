package net.phantara.prism.server.api.impl

import net.phantara.prism.api.properties.IServerProperties
import net.phantara.prism.server.PrismServer
import java.net.InetSocketAddress

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 14:08
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class ServerPropertiesImpl : IServerProperties {

    override fun getAddress(): InetSocketAddress {
        val host = PrismServer.instance.properties.getProperty("address", String::class.java)
        val port = PrismServer.instance.properties.getProperty("port", Int::class.java)
        return InetSocketAddress(host, port)
    }

    override fun getMaxPlayers(): Int {
        val maxPlayers = PrismServer.instance.properties.getProperty("player-limit", Int::class.java)
        return maxPlayers
    }

    override fun getMotd(): String {
        val motd = PrismServer.instance.properties.getProperty("motd", String::class.java)
        return motd
    }

    override fun isOnlineMode(): Boolean {
        val isOnlineMode = PrismServer.instance.properties.getProperty("online-mode", Boolean::class.java)
        return isOnlineMode
    }

    override fun isBungeeCordSupport(): Boolean {
        val isBungeeCordSupport = PrismServer.instance.properties.getProperty("bungeecord-support", Boolean::class.java)
        return isBungeeCordSupport
    }

    override fun isVelocitySupport(): Boolean {
        val isVelocitySupport = PrismServer.instance.properties.getProperty("velocity-support", Boolean::class.java)
        return isVelocitySupport
    }

    override fun getVelocitySecretKey(): String {
        val key = PrismServer.instance.properties.getProperty("velocity-secret-key", String::class.java)
        return key
    }
}