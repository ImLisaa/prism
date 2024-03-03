package net.phantara.prism.api.particle

import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.phantara.prism.api.particle.curve.IParticleCurve
import org.jetbrains.annotations.UnknownNullability
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:35
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class ParticleCircle : IParticleCurve {

    private var center: Point? = null
    private var radius by Delegates.notNull<Double>()
    private var currentDistance by Delegates.notNull<Double>()

    constructor(center: Point, radius: Double) {
        this.center = center
        this.radius = radius
        reset()
    }

    fun getLength(): Double {
        return 2 * Math.PI * radius
    }

    override fun getNextPoint(distance: Double, continueFromStart: Boolean): Point? {
        this.currentDistance += distance
        if (this.currentDistance > getLength()) {
            if (continueFromStart) {
                this.currentDistance %= getLength()
            } else {
                return null
            }
        }

        val angle = this.currentDistance / this.radius
        val relativePoint = Vec(cos(angle), sin(angle)).mul(this.radius)
        return this.center!!.add(relativePoint)
    }

    override fun reset() {
        this.currentDistance = 0.0
    }
}