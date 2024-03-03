package net.phantara.prism.server

import net.phantara.prism.api.PrismServerAPI
import net.phantara.prism.server.api.impl.InstanceFactoryImpl
import net.phantara.prism.server.api.impl.SchematicFactoryImpl
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
        PrismServerAPI(
            InstanceFactoryImpl(),
            ServerPropertiesImpl(),
            SchematicFactoryImpl()
        )
    }
}