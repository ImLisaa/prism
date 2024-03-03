package net.phantara.prism.api.inventory.item.impl

import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.phantara.prism.api.inventory.item.ClickType
import net.phantara.prism.api.inventory.item.IInventoryItem
import java.util.Arrays
import java.util.function.Consumer


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:05
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class ClickableItem(private val itemStack: ItemStack) : IInventoryItem {
    private val onClickMap: MutableMap<Consumer<Player>, ArrayList<ClickType>> =
        HashMap()

    override fun getItemStack(): ItemStack {
        return itemStack
    }

    fun subscribe(onClick: Consumer<Player>, vararg clickTypes: ClickType): ClickableItem {
        var typesToList = ArrayList<ClickType>()
        if (clickTypes.isEmpty()) {
            typesToList = arrayListOf(ClickType.ALL)
        }
        this.onClickMap[onClick] = typesToList.toList() as ArrayList<ClickType>
        return this
    }

    fun click(player: Player, identifier: String) {
        for ((key, value) in onClickMap) {
            for (clickType in value) {
                if (clickType.getIdentifier() == identifier || clickType.getIdentifier() == "ALL") {
                    key.accept(player)
                    break
                }
            }
        }
    }
}