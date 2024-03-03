package net.phantara.prism.api.schematic

import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import org.jglrxavpok.hephaistos.nbt.*
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import java.io.File
import java.io.IOException
import java.util.*

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:06
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class Schematic {

    private val blocks: MutableMap<Vec, Block> = LinkedHashMap()

    fun addBlock(vec: Vec, block: Block) {
        blocks[vec] = block
    }

    fun save(file: File) {
        val level = NBT.Compound { root: MutableNBTCompound ->
            val compounds = LinkedList<NBTCompound>()
            for (vec in blocks.keys) {
                val block = blocks[vec]
                compounds.add(NBT.Compound { it: MutableNBTCompound ->
                    it.setInt("x", vec.blockX()).setInt("y", vec.blockY()).setInt("z", vec.blockZ())
                        .setString("block", block!!.namespace().namespace())
                    it["hasData"] = NBT.Boolean(block.nbt() != null)
                    if (block.nbt() != null) {
                        it["data"] = block.nbt()!!
                    }
                })
            }
            root["blocks"] = NBT.List(NBTType.TAG_Compound, compounds)
        }
        try {
            NBTWriter(file, CompressedProcesser.GZIP).use { writer ->
                writer.writeNamed("", level)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun paste(pos: Pos, instance: Instance) {
        for (vec in blocks.keys) {
            instance.setBlock(pos.add(vec), blocks[vec]!!)
        }
    }
}