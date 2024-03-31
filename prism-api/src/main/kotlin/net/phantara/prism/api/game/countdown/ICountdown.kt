package net.phantara.prism.api.game.countdown

import com.google.common.annotations.Beta
import org.jetbrains.annotations.ApiStatus.Experimental
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval
import java.util.function.Predicate

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:13
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

@Beta
@Experimental
@ScheduledForRemoval
@Deprecated("Will be replaced with Kotlin Class soon.")
/**
 * No implementation yet. Will change to Kotlin Class soon.
 * TODO: Change Class from Interface and rebuild Class
 */
interface ICountdown {

    fun start()

    fun stop()

    fun reduce(seconds: Int)

    fun setStopCallback(stopCallback: Runnable): ICountdown

    fun setPredicate(predicate: Predicate<Void>): ICountdown

}