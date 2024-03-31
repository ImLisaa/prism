package net.phantara.prism.server.api.impl

import com.google.common.annotations.Beta
import com.google.common.collect.ImmutableSet
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.*
import net.minestom.server.utils.chunk.ChunkSupplier
import net.minestom.server.utils.chunk.ChunkUtils
import net.minestom.server.world.DimensionType
import net.phantara.prism.api.instance.IInstanceFactory
import net.phantara.prism.api.instance.type.InstanceType
import net.phantara.prism.server.api.json.Document
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import kotlin.io.path.Path

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:37
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class InstanceFactoryImpl : IInstanceFactory {

    val instances: HashMap<String, InstanceContainer> = HashMap()

    init {
        val mapPath = Path("maps")
        if (Files.notExists(mapPath)) {
            Files.createDirectory(mapPath)
        }
    }

    override fun createInstance(name: String, instanceType: InstanceType) {
        val instancePath = Path("maps", name)
        if (Files.notExists(instancePath)) {
            Files.createDirectory(instancePath)
            val containerId = UUID.randomUUID()
            val container = InstanceContainer(containerId, DimensionType.OVERWORLD, AnvilLoader(instancePath))
            container.chunkSupplier = ChunkSupplier { instance: Instance, chunkX: Int, chunkZ: Int ->
                LightingChunk(
                    instance, chunkX, chunkZ
                )
            }
            this.preLightAndLoadChunks(container)
            container.setGenerator(instanceType.generator)
            this.instances[name] = container
            this.writeIdToFile(containerId, instancePath)
            MinecraftServer.getInstanceManager().registerInstance(container)
            MinecraftServer.LOGGER.info("Created Instance ${containerId}.")
            return
        }
        loadInstance(name)
    }

    override fun loadInstance(name: String) {
        if (!this.instances.containsKey(name)) {
            val containerPath = Path("maps", name)
            val idPath = Path("maps", name, "id.json")
            if (Files.notExists(idPath)) {
                writeIdToFile(UUID.randomUUID(), idPath)
            }
            val document = Document(idPath)
            val containerId = UUID.fromString(document.get("identifier", String::class.java))
            val container = InstanceContainer(containerId, DimensionType.OVERWORLD, AnvilLoader(containerPath))
            container.chunkSupplier = ChunkSupplier { instance: Instance, chunkX: Int, chunkZ: Int ->
                LightingChunk(
                    instance, chunkX, chunkZ
                )
            }
            //this.preLightAndLoadChunks(container) - Crashes Server when loading for some Reason
            this.instances[name] = container
            MinecraftServer.getInstanceManager().registerInstance(container)
            MinecraftServer.LOGGER.info("Loaded Instance ${name}.")
        }
    }

    override fun unloadInstance(name: String) {
        getInstance(name).ifPresentOrElse({
            it.saveChunksToStorage().join()
            MinecraftServer.getInstanceManager().unregisterInstance(it)
            this.instances.remove(name)
            MinecraftServer.LOGGER.info("Instance $name unloaded.")
        }) {
            MinecraftServer.LOGGER.error("Instance $name is not loaded, so it cannot be unloaded.")
        }
    }

    override fun getInstance(name: String): Optional<InstanceContainer> {
        return Optional.ofNullable(this.instances[name])
    }

    override fun getInstances(): ImmutableSet<InstanceContainer> {
        return ImmutableSet.copyOf(this.instances.values)
    }

    private fun writeIdToFile(containerIdentifier: UUID, path: Path) {
        val document = Document()
        document
            .addIfNotExists("identifier", containerIdentifier.toString())
            .write(path)
    }

    @Beta
    private fun preLightAndLoadChunks(container: InstanceContainer) {
        val chunks = ArrayList<CompletableFuture<Chunk>>()
        ChunkUtils.forChunksInRange(0, 0, 32) { x, z ->
            chunks.add(container.loadChunk(x, z))
        }
        CompletableFuture.runAsync {
            CompletableFuture.allOf(*chunks.toTypedArray()).join()
            LightingChunk.relight(container, container.chunks)
        }
    }
}