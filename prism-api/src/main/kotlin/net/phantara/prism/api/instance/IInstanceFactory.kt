package net.phantara.prism.api.instance

import com.google.common.collect.ImmutableSet
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.InstanceContainer
import net.phantara.prism.api.instance.type.InstanceType
import java.io.FileFilter
import java.util.Optional
import kotlin.io.path.Path

/**
 * @author Lisa Kapahnke
 * @created 24.02.2024 | 23:48
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

interface IInstanceFactory {

    fun createInstance(name: String, instanceType: InstanceType)

    fun loadInstance(name: String)

    fun unloadInstance(name: String)

    fun getInstance(name: String) : Optional<InstanceContainer>

    fun getInstances(): ImmutableSet<InstanceContainer>

    fun loadAllInstances() {
        val files = Path("maps").toFile().listFiles(FileFilter { it.isDirectory })
        if (files == null || files.isEmpty()) {
            MinecraftServer.LOGGER.warn("No instances to load.")
            return
        }
        files.forEach {
            loadInstance(it.name)
        }
    }

}