package mee.example.untitled1

import mee.example.untitled1.event.signEvent
import org.bukkit.entity.Entity
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class EventListener(private val plugin: JavaPlugin): Listener {
    val signEventInstance = signEvent
    var gameStartJudge = true

    @EventHandler
    private fun onPlayerInteract(event: PlayerInteractEvent) {//PlayerInteractEventはプレイヤーがブロックを右または左クリックを行ったときに呼び出される
        val player = event.player//イベントを起こしたプレイヤーの情報が入る。
        val block = event.clickedBlock
        //スタート処理
        if (block != null && block.type.name.contains("ORK_WALL_SIGN") && gameStartJudge) {
            gameStartJudge = false
            signEventInstance.singWriteStart(plugin,event)
        }
    }

    @EventHandler
    private fun onPlayerInteractVillager(event: PlayerInteractEntityEvent){
        val clickedEntity = event.rightClicked
        if(clickedEntity is Villager){
            event.isCancelled = true
        }
    }
}