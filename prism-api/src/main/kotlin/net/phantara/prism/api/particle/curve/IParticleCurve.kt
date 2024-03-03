package net.phantara.prism.api.particle.curve

import net.minestom.server.coordinate.Point
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.UnknownNullability



/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:28
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

interface IParticleCurve {

    @NotNull
    fun getNextPoint(distance: Double): Point? {
        return getNextPoint(distance, true)
    }

    fun getNextPoint(distance: Double, continueFromStart: Boolean): @UnknownNullability Point?
    fun reset()
}