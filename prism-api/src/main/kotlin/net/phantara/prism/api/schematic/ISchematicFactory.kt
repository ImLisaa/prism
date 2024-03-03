package net.phantara.prism.api.schematic

import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.Instance
import java.nio.file.Path

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:06
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

interface ISchematicFactory {

    fun scan(instance: Instance, posOne: Pos, posTwo: Pos, copyPos: Pos): Schematic

    fun read(path: Path): Schematic

}