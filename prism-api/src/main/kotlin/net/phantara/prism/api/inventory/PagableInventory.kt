package net.phantara.prism.api.inventory

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.phantara.prism.api.inventory.item.impl.ClickableItem
import kotlin.math.min


/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:24
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

abstract class PageableInventory<T>(
    type: InventoryType, title: Component, clickable: Boolean,
    private val possibleSlots: IntArray, values: List<T>,
    nextPageItem: ItemStack,
    previousPageItem: ItemStack
) :
    SingletonInventory(type, clickable, title) {
    private val elements = values

    private var currentPage = 1
    private val localNextPageItem = ClickableItem(nextPageItem).subscribe(
        { _: Player ->
            buildPage(
                ++currentPage
            )
        })
    private val localBehaviorPageItem = ClickableItem(previousPageItem).subscribe(
        { _: Player ->
            buildPage(
                --currentPage
            )
        })

    init {
        this.fill()
        this.buildPage(1)
    }

    fun fill() {}

    abstract fun constructItem(value: T): ClickableItem

    fun onChangePage(pageableInventory: PageableInventory<T>) {}

    fun calculateNextPageSlot(): Int {
        return getInventory().size - 1
    }

    fun calculateBehaviorPageSlot(): Int {
        return getInventory().size - 9
    }

    private fun buildPage(id: Int) {
        this.currentPage = id
        this.clear()

        if (currentPage > 1) {
            setItem(calculateBehaviorPageSlot(), localBehaviorPageItem)
        } else {
            getInventory().setItemStack(calculateBehaviorPageSlot(), ItemStack.AIR)
            getItems().remove(calculateBehaviorPageSlot())
        }


        if (elements.size == possibleSlots.size) {
            getInventory().setItemStack(calculateNextPageSlot(), ItemStack.AIR)
            getItems().remove(calculateNextPageSlot())
        } else if (currentPage < getMaximalPage()) {
            setItem(calculateNextPageSlot(), localNextPageItem)
        } else {
            getInventory().setItemStack(calculateNextPageSlot(), ItemStack.AIR)
            getItems().remove(calculateNextPageSlot())
        }

        for ((stepId, element) in elements.subList(
            possibleSlots.size * (currentPage - 1),
            min(elements.size.toDouble(), (possibleSlots.size * (currentPage - 1) + possibleSlots.size).toDouble())
                .toInt()
        ).withIndex()) {
            setItem(possibleSlots[stepId], constructItem(element))
        }
        onChangePage(this)
    }

    override fun clear() {
        for (possibleSlot in possibleSlots) getInventory().setItemStack(possibleSlot, ItemStack.AIR)
    }

    fun getMaximalPage(): Int {
        return elements.size / possibleSlots.size
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun getElements(): List<T> {
        return elements
    }
}