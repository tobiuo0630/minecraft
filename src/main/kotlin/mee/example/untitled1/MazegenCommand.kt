package mee.example.untitled1

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MazegenCommand(private val plugin: Untitled1): CommandExecutor {
    override fun onCommand(sender: CommandSender,
                           command: Command,
                           label: String,
                           args: Array<out String>):Boolean{
        if(sender !is Player){//:実行者がプレイヤーではないとき失敗
            return false
        }
        val player = sender
        val start = player.location.clone()

        plugin.server.scheduler.runTask(plugin, Runnable{
            Mazegene.generateMaze(start,10,10,player.world)
        })
        return true
    }
}