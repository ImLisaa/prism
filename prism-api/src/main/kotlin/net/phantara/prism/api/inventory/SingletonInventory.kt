package net.phantara.prism.api.inventory

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.click.ClickType
import net.minestom.server.inventory.condition.InventoryConditionResult
import net.minestom.server.item.ItemStack
import net.phantara.prism.api.inventory.item.IInventoryItem
import net.phantara.prism.api.inventory.item.impl.ClickableItem
import java.util.concurrent.ConcurrentHashMap


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:21
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

open class SingletonInventory(inventoryType: InventoryType, clickable: Boolean, title: Component) :
    IInventoryConstruct {
    private val inventory = Inventory(inventoryType, title)
    private val items: ConcurrentHashMap<Int, IInventoryItem> = ConcurrentHashMap<Int, IInventoryItem>()

    init {
        inventory.addInventoryCondition { player: Player, slot: Int, clickType: ClickType, result: InventoryConditionResult ->
            result.isCancel = !clickable
            if (items.containsKey(slot) && items[slot] is ClickableItem) {
                val clickableItem = items[slot] as ClickableItem
                clickableItem.click(player, clickType.name)
            }
        }
    }

    override fun setItem(slot: Int, stack: IInventoryItem) {
        inventory.setItemStack(slot, stack.getItemStack())
        items[slot] = stack
    }

    override fun addItem(stack: IInventoryItem) {
        val slot = this.getNextSlot()
        inventory.setItemStack(slot, stack.getItemStack())
        items[slot] = stack
    }

    override fun removeItem(slot: Int) {
        inventory.setItemStack(slot, ItemStack.AIR)
        items.remove(slot)
    }

    override fun removeItem(clickableItem: IInventoryItem) {
        removeItem(this.getSlotByClickedItem(clickableItem))
    }

    override fun open(player: Player) {
        player.openInventory(this.inventory)
    }

    override fun clear() {
        inventory.clear()
    }

    override fun setTitle(title: Component) {
        inventory.title = title
    }

    private fun getNextSlot(): Int {
        for (i in 0 until inventory.size) if (!items.containsKey(i)) return i
        return -1
    }

    private fun getSlotByClickedItem(clickableItem: IInventoryItem): Int {
        for (slot in items.keys) if (items[slot]!! == clickableItem) return slot
        return -1
    }

    open fun getInventory(): Inventory {
        return inventory
    }

    fun getItems(): ConcurrentHashMap<Int, IInventoryItem> {
        return items
    }
}