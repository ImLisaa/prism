package net.phantara.prism.api.particle

import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.timer.Task
import net.minestom.server.timer.TaskSchedule
import net.phantara.prism.api.particle.builder.ParticleBuilder
import java.util.function.Predicate
import kotlin.properties.Delegates


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:25
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

abstract class AbstractParticleQueue {

    private var builder: ParticleBuilder
    private var speed by Delegates.notNull<Int>()
    private var instance: Instance
    private var visibility: Predicate<Player>

    private var task: Task? = null

    constructor(
        builder: ParticleBuilder,
        speed: Int,
        instance: Instance,
        visibility: Predicate<Player>
    ) {
        this.builder = builder
        this.speed = speed
        this.instance = instance
        this.visibility = visibility
    }

    constructor(builder: ParticleBuilder, speed: Int, instance: Instance) : this(builder, speed, instance, { true })

    fun getInstance(): Instance {
        return this.instance
    }

    fun getBuilder(): ParticleBuilder {
        return this.builder
    }

    abstract fun tick()

    fun stop() {
        if (this.task != null) {
            this.task!!.cancel()
            this.task = null
        }
    }

    fun run() {
        this.task = MinecraftServer.getSchedulerManager().submitTask {
            tick()
            TaskSchedule.millis(this.speed.toLong())
        }
    }

    fun setVisibility(visibility: Predicate<Player>) {
        this.visibility = visibility
    }

    fun sendParticle(point: Point) {
        this.instance.players.stream().filter(this.visibility).forEach { p -> getBuilder().send(p, point) }
    }
}