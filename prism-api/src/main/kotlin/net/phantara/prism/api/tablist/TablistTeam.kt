package net.phantara.prism.api.tablist

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.entity.Player
import net.minestom.server.network.packet.server.play.TeamsPacket
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule
import net.minestom.server.scoreboard.Team
import net.minestom.server.scoreboard.TeamBuilder
import net.minestom.server.scoreboard.TeamManager
import java.util.function.Predicate

/**
 * @author Lisa Kapahnke
 * @created 03.03.2024 | 15:00
 * @contact @imlisaa_ (Discord)
 *
 * You are not allowed to modify or make changes to
 * this file without permission.
 **/

class TablistTeam(
    var name: String,
    var id: Int,
    var prefix: Component,
    var textColor: NamedTextColor,
    var collisionRule: CollisionRule,
    var predicate: Predicate<Player>
) {

    private lateinit var team: Team

    fun toTeam(manager: TeamManager) {
        this.team = TeamBuilder(this.name, manager).updateTeamColor(this.textColor).updatePrefix(this.prefix)
            .collisionRule(this.collisionRule).build()
    }
}