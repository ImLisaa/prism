package net.phantara.prism.api.particle.builder

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.network.packet.server.play.ParticlePacket
import net.minestom.server.particle.Particle
import java.nio.ByteBuffer;


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:25
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class ParticleBuilder {
    private lateinit var particle: Particle

    private var count = 0
    private var speed = 0f

    private var data = ByteArray(0)

    private var offsetX = 0f
    private var offsetY = 0f
    private var offsetZ = 0f

    constructor(r: Float, g: Float, b: Float) {
        ParticleBuilder(r, g, b, 1, 0f)
    }

    constructor(r: Float, g: Float, b: Float, count: Int, speed: Float) {
        ParticleBuilder(Particle.DUST, count, speed)
        val scale = 0.8f
        val data: ByteBuffer = ByteBuffer.allocate(16)
        data.putFloat(r / 255f)
        data.putFloat(g / 255f)
        data.putFloat(b / 255f)
        data.putFloat(scale)
        this.data = data.array()
    }

    constructor(particle: Particle) {
        ParticleBuilder(particle, 1, 0f)
    }

    constructor(particle: Particle, count: Int, speed: Float) {
        this.particle = particle
        this.count = count
        this.speed = speed
    }

    fun send(player: Player, point: Point) {
        player.sendPacket(toPacket(point))
    }

    fun withOffSet(x: Float, y: Float, z: Float): ParticleBuilder {
        offsetX = x
        offsetY = y
        offsetZ = z
        return this
    }

    private fun toPacket(point: Point): ParticlePacket {
        return ParticlePacket(
            particle.id(),
            false,
            point.x(),
            point.y(),
            point.z(),
            offsetX,
            offsetY,
            offsetZ,
            speed,
            count,
            data
        )
    }
}