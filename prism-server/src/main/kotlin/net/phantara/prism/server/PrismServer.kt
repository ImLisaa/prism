package net.phantara.prism.server

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.ExtensionManager
import net.minestom.server.extras.MojangAuth
import net.minestom.server.extras.bungee.BungeeCordProxy
import net.minestom.server.extras.velocity.VelocityProxy
import net.phantara.prism.api.PrismServerAPI
import net.phantara.prism.server.api.impl.InstanceFactoryImpl
import net.phantara.prism.server.api.impl.ServerPropertiesImpl
import net.phantara.prism.server.api.properties.PrismServerProperties

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

    var properties: PrismServerProperties

    init {
        instance = this
        this.properties = PrismServerProperties()

        val server = MinecraftServer.init()

        val extensionManager = ExtensionManager(MinecraftServer.process())
        extensionManager.start()

        extensionManager.gotoPreInit()

        PrismServerAPI(
            InstanceFactoryImpl(),
            ServerPropertiesImpl()
        )

        setupProperties()

        extensionManager.gotoInit()

        System.setProperty("minestom.chunk-view-distance", "10")
        System.setProperty("minestom.entity-view-distance", "32")

        MinecraftServer.setBrandName("Prism 1.21")
        MinecraftServer.getSchedulerManager().buildShutdownTask {
            extensionManager.shutdown()
        }

        server.start(
            PrismServerAPI.instance.serverProperties.getAddress().host,
            PrismServerAPI.instance.serverProperties.getAddress().port
        )

        extensionManager.gotoPostInit()
    }

    private fun setupProperties() {
        if (PrismServerAPI.instance.serverProperties.isOnlineMode()) {
            MojangAuth.init()
        }

        if (PrismServerAPI.instance.serverProperties.isBungeeCordSupport()) {
            BungeeCordProxy.enable()
        }

        if (PrismServerAPI.instance.serverProperties.isBungeeCordSupport() && PrismServerAPI.instance.serverProperties.isVelocitySupport()) {
            MinecraftServer.LOGGER.error("Bungeecord-Support and Velocity-Support conflict with each-other. Disabling both.")
            this.properties.setProperty("velocity-support", false)
            this.properties.setProperty("buneecord-support", false)
            this.properties.updateDocument()
            this.properties.reload()
        }

        if (PrismServerAPI.instance.serverProperties.isVelocitySupport()) {
            VelocityProxy.enable(PrismServerAPI.instance.serverProperties.getVelocitySecretKey())
        }
    }
}