package net.phantara.prism.api.clickable

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerUseItemEvent
import net.minestom.server.item.ItemStack
import java.util.function.Consumer

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 00:50
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class InteractItem(var item: ItemStack, var playerInteraction: Consumer<Player>) {

    companion object {
        @JvmStatic
        val ITEM_LIST: ArrayList<InteractItem> = ArrayList()

        fun initializeParticleQueue() {
            MinecraftServer.getGlobalEventHandler().addListener(PlayerUseItemEvent::class.java) { event ->
                ITEM_LIST.forEach {
                    if (event.player.itemInMainHand == it.item) {
                        it.playerInteraction.accept(event.player)
                        return@forEach
                    }
                }
            }
        }
    }

    init {
        ITEM_LIST.add(this)
    }

    fun equip(player: Player, slot: Int) {
        player.inventory.setItemStack(slot, this.item)
    }

    fun remove() {
        ITEM_LIST.remove(this)
    }

}