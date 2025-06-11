package mee.example.untitled1.event

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class sign(private val plugin: JavaPlugin) {
    val magen_get_key = "めいろのかぎをとる"
    val battle_get_key = "ばとるのかぎをとる"
    val rast_boss_key = "さいごのかぎをとる"


    @EventHandler
    public fun singTemp(event: PlayerInteractEvent,plugin: JavaPlugin) {
        val player = event.player
        //鍵を入手
        if (event.hand != EquipmentSlot.HAND) return//オフハンドのイベントを無視
        val block = event.clickedBlock


        event.isCancelled = true
        val worldstart = player.world
        val st = block?.state
        if (st is Sign) { val linestart = st.getSide(Side.FRONT).getLine(0).trim()
            if (linestart == "げーむすたーと") {
                    val locationstart = Location(worldstart, 4248.7, 86.56250, 5846.988)//bedの座標
                    locationstart.yaw = 0f
                    locationstart.pitch = 0f
                    player.teleport(locationstart)
                    player.sendTitle("tmep", "temper", 10, 100, 20)
                    val total = 10 + 100 + 20
                    //最初のミッション
                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        missionchat(player,1,"きのけんをつくろう")
                    }, total.toLong())
                }
            }
        }


}
}