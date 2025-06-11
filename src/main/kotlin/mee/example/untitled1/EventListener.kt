package mee.example.untitled1

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

class EventListener(private val plugin: JavaPlugin): Listener {
    //鍵の使用状況falseが使っていない.


    @EventHandler
    private fun onPlayerInteract(event: PlayerInteractEvent) {//PlayerInteractEventはプレイヤーが右または左クリックを行ったときに呼び出される
        val player = event.player//イベントを起こしたプレイヤーの情報が入る。

        val block = event.clickedBlock
        //スタート処理
        if(block != null && block.type.name.contains("ORK_WALL_SIGN")){
            print("a")
        }

}

