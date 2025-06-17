package mee.example.untitled1.event

import mee.example.untitled1.missionBoard.missionUpdate
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.java.JavaPlugin

object signEvent {

    val magen_get_key = "めいろのかぎをとる"
    val battle_get_key = "ばとるのかぎをとる"
    val rast_boss_key = "さいごのかぎをとる"

    fun singWriteStart(plugin: JavaPlugin, event: PlayerInteractEvent){
        val player = event.player
        //鍵を入手
        if (event.hand != EquipmentSlot.HAND) return//オフハンドのイベントを無視
        val blockInfo = event.clickedBlock


        event.isCancelled = true
        val worldstart = player.world
        val blockState = blockInfo?.state
        if (blockState is Sign) {
            val linestart = blockState.getSide(Side.FRONT).getLine(0).trim()
            if (linestart == "げーむすたーと") {
                val locationstart = Location(worldstart, 4248.7, 86.56250, 5846.988)//bedの座標
                locationstart.yaw = 0f
                locationstart.pitch = 0f
                player.teleport(locationstart)
                player.sendTitle("tmep", "temper", 10, 100, 20)
                val total = 10 + 100 + 20
                //最初のミッションを表示
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    missionUpdate(player, 1, "きのけんをつくろう")
                }, total.toLong())
            }
        }
    }

}