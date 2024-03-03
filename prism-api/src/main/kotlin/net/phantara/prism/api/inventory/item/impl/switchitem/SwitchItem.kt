package net.phantara.prism.api.inventory.item.impl.switchitem

import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.phantara.prism.api.inventory.SingletonInventory
import net.phantara.prism.api.inventory.item.IInventoryItem
import net.phantara.prism.api.inventory.item.impl.ClickableItem
import java.util.function.Consumer
import java.util.function.Predicate


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:35
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class SwitchItem(item: ItemStack, consumer: Consumer<Player>, predicate: Predicate<Player>) :
    IInventoryItem {
    private val switches: MutableList<SwitchEntry> = ArrayList()

    private var currentItem = 0

    init {
        addSwitch(ClickableItem(item), consumer, predicate)
    }

    fun addSwitch(item: IInventoryItem, onSwitch: Consumer<Player>, predicate: Predicate<Player>): SwitchItem {
        switches.add(SwitchEntry(item, onSwitch, predicate))
        return this
    }

    override fun getItemStack(): ItemStack {
        return switches[currentItem].item.getItemStack()
    }

    fun click(clickType: String, player: Player, inventory: SingletonInventory, slot: Int) {
        var nextIndex = currentItem + (if ((clickType == "RIGHT_CLICK" || clickType == "SHIFT_RIGHT_CLICK")) -1 else 1)
        if (nextIndex >= switches.size) {
            nextIndex = 0
        } else if (nextIndex < 0) {
            nextIndex = switches.size - 1
        }

        val nextItem = switches[nextIndex]
        if (!nextItem.predicate.test(player)) {
            return
        }

        currentItem = nextIndex
        nextItem.consumer.accept(player)
        inventory.setItem(slot, this)
    }
}