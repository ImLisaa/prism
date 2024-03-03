package net.phantara.prism.api.instance.type

import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.generator.GenerationUnit
import net.minestom.server.instance.generator.Generator

/**
 * @author Lisa Kapahnke
 * @created 24.02.2024 | 23:50
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

enum class InstanceType(val generator: Generator) {

    FLAT(FlatGenerator()),
    VOID(VoidGenerator());


    private class FlatGenerator : Generator {
        override fun generate(unit: GenerationUnit) {
            unit.modifier().fillHeight(-1, 0, Block.GRASS_BLOCK)
            unit.modifier().fillHeight(-2, -1, Block.DIRT)
            unit.modifier().fillHeight(-3, -2, Block.DIRT)
            unit.modifier().fillHeight(-4, -3, Block.BEDROCK)
        }
    }

    private class VoidGenerator : Generator {
        override fun generate(unit: GenerationUnit) {
            unit.modifier().fill(Pos(0.0, 0.0, 0.0), Pos(0.0, 0.0, 0.0), Block.GOLD_BLOCK)
        }
    }
}