package net.phantara.prism.api.inventory.item

/**
 * @author Lisa Kapahnke
 * @created 25.02.2024 | 01:08
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

enum class ClickType(private val identifier: String) {
    ALL("ALL"),
    LEFT("LEFT_CLICK"),
    RIGHT("RIGHT_CLICK"),
    SHIFT("START_SHIFT_CLICK"),
    DOUBLE("START_DOUBLE_CLICK");

    fun getIdentifier(): String {
        return identifier
    }
}