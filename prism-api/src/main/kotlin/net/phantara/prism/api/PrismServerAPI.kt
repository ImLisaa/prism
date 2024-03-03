package net.phantara.prism.api

import net.phantara.prism.api.instance.IInstanceFactory
import net.phantara.prism.api.properties.IServerProperties
import net.phantara.prism.api.schematic.ISchematicFactory

/**
 * @author Lisa Kapahnke
 * @created 24.02.2024 | 23:47
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class PrismServerAPI(
    val instanceFactory: IInstanceFactory,
    val serverProperties: IServerProperties,
    val schematicFactory: ISchematicFactory

) {

    companion object {
        @JvmStatic
        lateinit var instance: PrismServerAPI
    }


    init {
        instance = this
    }
}