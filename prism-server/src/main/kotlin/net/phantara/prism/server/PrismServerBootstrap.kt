package net.phantara.prism.server

import net.minestom.server.MinecraftServer

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 13:52
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

fun main() {
    PrismServer()

    Runtime.getRuntime().addShutdownHook(Thread {
        MinecraftServer.LOGGER.info("Prism is shutting down.")
        MinecraftServer.getInstanceManager().instances.forEach {
            it.saveChunksToStorage().join()
        }
    })
}