package net.phantara.prism.api.inventory

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.phantara.prism.api.inventory.item.IInventoryItem
import org.jetbrains.annotations.NotNull

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:04
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

interface IInventoryConstruct {

    fun setItem(slot: Int, @NotNull stack: IInventoryItem)

    fun addItem(@NotNull stack: IInventoryItem)

    fun removeItem(slot: Int)

    fun removeItem(@NotNull clickableItem: IInventoryItem)

    fun open(@NotNull player: Player)

    fun clear()

    fun setTitle(title: Component)
}