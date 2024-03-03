package net.phantara.prism.server.api.impl

import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.phantara.prism.api.schematic.ISchematicFactory
import net.phantara.prism.api.schematic.Schematic
import org.jglrxavpok.hephaistos.nbt.*
import java.io.IOException
import java.nio.file.Path
import java.util.*

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 14:25
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class SchematicFactoryImpl : ISchematicFactory {

    override fun scan(instance: Instance, posOne: Pos, posTwo: Pos, copyPos: Pos): Schematic {
        val schematic = Schematic()
        val (minX, maxX) = listOf(posOne.blockX(), posTwo.blockX()).sorted()
        val (minY, maxY) = listOf(posOne.blockY(), posTwo.blockY()).sorted()
        val (minZ, maxZ) = listOf(posOne.blockZ(), posTwo.blockZ()).sorted()

        for (x in minX..maxX) {
            for (z in minZ..maxZ) {
                for (y in minY..maxY) {
                    instance.getBlock(x, y, z, Block.Getter.Condition.TYPE)?.takeUnless(Block::isAir)
                        ?.let { schematic.addBlock(Vec(x.toDouble(), y.toDouble(), z.toDouble()).sub(copyPos), it) }
                }
            }
        }
        return schematic
    }

    override fun read(path: Path): Schematic {
        val schematic = Schematic()
        NBTReader(path.toFile(), CompressedProcesser.GZIP).use { reader ->
            val tag = reader.read() as NBTCompound
            for (block in Objects.requireNonNull(tag.getList<NBT>("blocks"))!!) {
                val compound = block as NBTCompound
                val x = compound.getInt("x")
                val y = compound.getInt("y")
                val z = compound.getInt("z")
                val name =
                    Block.fromNamespaceId(
                        compound.getString("block")!!
                    )
                val hasData = compound.getBoolean("hasData")
                if (hasData!!) {
                    val data = compound.getCompound("data")
                    schematic.addBlock(Vec(x!!.toDouble(), y!!.toDouble(), z!!.toDouble()), name!!.withNbt(data))
                } else {
                    schematic.addBlock(Vec(x!!.toDouble(), y!!.toDouble(), z!!.toDouble()), name!!)
                }
            }
        }
        return schematic
    }
}