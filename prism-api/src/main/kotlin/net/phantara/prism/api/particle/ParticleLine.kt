package net.phantara.prism.api.particle

import net.minestom.server.coordinate.Point
import org.jetbrains.annotations.UnknownNullability
import kotlin.properties.Delegates


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:38
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class ParticleLine {

    private var vertices: Array<Point>
    private var lengths: DoubleArray
    private var currentPosition by Delegates.notNull<Double>()
    private var currentEdge by Delegates.notNull<Int>()

    constructor(vertices: Array<Point>) {
        this.vertices = vertices.clone()
        this.lengths = DoubleArray(vertices.size - 1)
        for (i in this.lengths.indices) {
            this.lengths[i] = vertices[i].distance(vertices[i + 1])
        }
        reset()
    }

    fun getNextPoint(distance: Double, continueFromStart: Boolean): @UnknownNullability Point? {
        this.currentPosition += distance
        while (this.currentPosition >= this.lengths[this.currentEdge]) {
            this.currentPosition -= this.lengths[this.currentEdge]
            this.currentEdge++
            if (this.currentEdge >= this.lengths.size) {
                if (continueFromStart) {
                    this.currentEdge = 0
                } else {
                    return null
                }
            }
        }
        val edge: Point = this.vertices[this.currentEdge + 1].sub(this.vertices[this.currentEdge])
        return this.vertices[this.currentEdge].add(edge.mul(this.currentPosition / this.lengths[this.currentEdge]))
    }

    fun reset() {
        currentEdge = 0
        currentPosition = 0.0
    }
}