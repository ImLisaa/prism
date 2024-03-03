package net.phantara.prism.api.game.countdown

import java.util.function.Predicate

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:13
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

interface ICountdown {

    fun start()

    fun stop()

    fun reduce(seconds: Int)

    fun setStopCallback(stopCallback: Runnable): ICountdown

    fun setPredicate(predicate: Predicate<Void>): ICountdown

}