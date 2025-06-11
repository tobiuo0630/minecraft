package mee.example.untitled1

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

object missionBoard {
    fun missionUpdate(player: Player, missionNumber: Int, mission: String){
        player.sendMessage(mission)
        val manager = Bukkit.getScoreboardManager() ?: return

        val board = manager.newScoreboard//スコアボードの土台、白紙だと考える
        val objective = board.registerNewObjective("mission", Criteria.DUMMY,"${ChatColor.RED}${ChatColor.BOLD}もくひょう")//白紙の上に貼る文字、数字の定義をする
        objective.displaySlot = DisplaySlot.SIDEBAR
        val mit = objective.getScore(mission)
        mit.score = missionNumber
        player.scoreboard = board
    }
}