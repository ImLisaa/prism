package net.phantara.prism.api.inventory.item.impl.switchitem

import net.minestom.server.entity.Player
import net.phantara.prism.api.inventory.item.IInventoryItem
import java.util.function.Consumer
import java.util.function.Predicate


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:34
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

@JvmRecord
data class SwitchEntry(val item: IInventoryItem, val consumer: Consumer<Player>, val predicate: Predicate<Player>)