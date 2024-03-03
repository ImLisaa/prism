package net.phantara.prism.api.particle.curve.queue.permanent

import net.minestom.server.coordinate.Point
import net.minestom.server.instance.Instance
import net.phantara.prism.api.particle.AbstractParticleQueue
import net.phantara.prism.api.particle.builder.ParticleBuilder
import net.phantara.prism.api.particle.curve.IParticleCurve


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:44
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class PermanentParticleCurveQueue : AbstractParticleQueue {

    private var curve: IParticleCurve
    private var spacing = 0.0

    constructor(
        builder: ParticleBuilder,
        updateSpeed: Int,
        spacing: Double,
        instance: Instance,
        curve: IParticleCurve
    ) : super(builder, updateSpeed, instance) {
        this.curve = curve
        this.spacing = spacing
    }

    override fun tick() {
        curve.reset()
        while (true) {
            val point: Point = curve.getNextPoint(spacing, false) ?: return
            sendParticle(point)
        }
    }
}