package net.phantara.prism.api.particle.curve.queue

import net.minestom.server.coordinate.Point
import net.minestom.server.instance.Instance
import net.phantara.prism.api.particle.AbstractParticleQueue
import net.phantara.prism.api.particle.builder.ParticleBuilder
import net.phantara.prism.api.particle.curve.IParticleCurve
import kotlin.properties.Delegates


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:40
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class ParticleCurveQueue : AbstractParticleQueue {

    private var curve: IParticleCurve
    private var speed by Delegates.notNull<Double>()
    private var deltaTime by Delegates.notNull<Double>()

    constructor(
        builder: ParticleBuilder,
        updateSpeed: Int,
        speed: Double,
        instance: Instance,
        curve: IParticleCurve
    ) : super(builder, updateSpeed, instance) {
        this.curve = curve
        this.speed = speed.toInt().toDouble()
        deltaTime = updateSpeed / 1000.0
    }

    override fun tick() {
        val point: Point = curve.getNextPoint(speed * deltaTime)!!
        sendParticle(point)
    }
}