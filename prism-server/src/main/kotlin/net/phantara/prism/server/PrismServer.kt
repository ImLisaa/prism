package net.phantara.prism.server

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.ExtensionManager
import net.minestom.server.extras.velocity.VelocityProxy
import net.phantara.prism.api.PrismServerAPI
import net.phantara.prism.server.api.impl.InstanceFactoryImpl
import net.phantara.prism.server.api.impl.SchematicFactoryImpl
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:37
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class PrismServer {

    companion object {
        @JvmStatic
        lateinit var instance: PrismServer
            private set
    }

    init {
        instance = this

        val server = MinecraftServer.init()
        val extensionManager = ExtensionManager(MinecraftServer.process())
        extensionManager.start()

        PrismServerAPI(
            InstanceFactoryImpl(),
            SchematicFactoryImpl()
        )

        extensionManager.gotoPreInit()

        setupProperties()

        System.setProperty("minestom.chunk-view-distance", "10")
        System.setProperty("minestom.entity-view-distance", "32")

        MinecraftServer.setBrandName("Prism")

        MinecraftServer.getSchedulerManager().buildShutdownTask {
            extensionManager.shutdown()
        }

        val host = System.getenv("RC_HOST")
        val port = System.getenv("RC_PORT").toInt()

        extensionManager.gotoInit()

        server.start(host, port)

        MinecraftServer.LOGGER.info("Started Prism-Server on ${host}:${port}.")

        extensionManager.gotoPostInit()
    }

    private fun setupProperties() {
        VelocityProxy.enable(System.getenv("PROXY_SECRET"))

    }
}