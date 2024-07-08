package net.phantara.prism.api.properties

import java.net.InetSocketAddress

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:01
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

interface IServerProperties {

    fun getAddress(): InetSocketAddress
    fun getMaxPlayers(): Int
    fun getMotd(): String
    fun isOnlineMode(): Boolean
    fun isBungeeCordSupport(): Boolean
    fun isVelocitySupport(): Boolean
    fun getVelocitySecretKey(): String
}