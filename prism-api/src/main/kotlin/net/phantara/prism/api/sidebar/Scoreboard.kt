package net.phantara.prism.api.sidebar

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.minestom.server.scoreboard.Sidebar
import net.minestom.server.scoreboard.Sidebar.ScoreboardLine

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 14:58
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class Scoreboard(component: Component) {

    private var sidebar: Sidebar = Sidebar(component)

    fun setLines(vararg lines: Component) {
        for (i in lines.indices) {
            sidebar.createLine(ScoreboardLine("line-$i", lines[i], lines.size - i))
        }
    }

    fun addPlayer(player: Player) {
        sidebar.addViewer(player)
    }

    fun removePlayer(player: Player) {
        sidebar.removeViewer(player)
    }

    fun addPlayers(vararg players: Player) {
        for (player in players) {
            sidebar.addViewer(player)
        }
    }

    fun removePlayers(vararg players: Player) {
        for (player in players) {
            sidebar.removeViewer(player)
        }
    }

    fun getSidebar(): Sidebar {
        return sidebar
    }
}