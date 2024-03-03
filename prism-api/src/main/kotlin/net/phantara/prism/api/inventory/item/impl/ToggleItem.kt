package net.phantara.prism.api.inventory.item.impl

import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.phantara.prism.api.inventory.SingletonInventory
import net.phantara.prism.api.inventory.item.IInventoryItem
import java.util.function.Predicate


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:15
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class ToggleItem(private val itemStack: ItemStack, private val change: IInventoryItem) : IInventoryItem {

    private var currentItem = 0
    private lateinit var predicate: Predicate<Player>

    constructor(itemStack: ItemStack, change: IInventoryItem, predicate: Predicate<Player>) : this(itemStack, change) {
        this.predicate = predicate
    }

    override fun getItemStack(): ItemStack {
        return if (currentItem == 0) itemStack else this.change.getItemStack()
    }

    fun click(player: Player, inventory: SingletonInventory, slot: Int) {
        if (!predicate.test(player)) {
            return
        }
        currentItem = 1 - currentItem
        inventory.setItem(slot, this)
    }
}