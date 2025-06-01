package mee.example.untitled1

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.entity.Player
import org.bukkit.entity.Slime
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Score
import org.bukkit.scoreboard.ScoreboardManager
import org.bukkit.util.NumberConversions.round
import kotlin.math.exp
import kotlin.math.pow

class EventListener(private val plugin: JavaPlugin): Listener {
    //鍵の使用状況falseが使っていない.
    var wood_hoe_use = false
    var stone_hoe_use = false
    var iron_hoe_use = false

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent){
        val player = event.player
        var start = true
        val use_hoe = arrayOf("wood","stone","iron")
        val magen_get_key = "めいろのかぎをとる"
        val battle_get_key = "ばとるのかぎをとる"
        val rast_boss_key = "さいごのかぎをとる"

        //鍵を入手
        if(event.hand != EquipmentSlot.HAND) return//オフハンドのイベントを無視
        val block = event.clickedBlock

        //ゲームスタート処理
        if(block != null && block.type.name.contains("CHERRY_WALL_SIGN") && start){
            start = false
            event.isCancelled = true
            val worldstart = player.world
            val st = block.state
            if(st is Sign){
                val linestart = st.getSide(Side.FRONT).getLine(0).trim()
                if(linestart=="げーむすたーと"){
                    val locationstart  = Location(worldstart,4248.7,86.56250,5846.988)//bedの座標
                    locationstart.yaw = 0f
                    locationstart.pitch = 0f
                    player.teleport(locationstart)
                    player.sendTitle("tmep","temper",10,100,20)
                    val total = 10+100+20
                    Bukkit.getScheduler().runTaskLater(plugin,Runnable{
                        gamestart(player)
                    },total.toLong())
                }
            }
        }

        //ダンジョン移動処理
        if(block != null && block.type.name.contains("OAK_WALL_SIGN")){
            //event.isCancelled = true
            val state = block.state
            if(state is Sign){
                val line = state.getSide(Side.FRONT).getLine(0).trim()
                player.sendMessage("DEBUG: sign line='$line'")

                if(line.equals(magen_get_key,ignoreCase = false)){//ignoreCaseは文字列の比較を大文字、小文字の区別せず行う
                    val hoe = ItemStack(Material.WOODEN_HOE)
                    player.inventory.addItem(hoe)
                    player.sendMessage("きのかぎをてにいれた")
                    return
                }else if(line.equals(battle_get_key,ignoreCase = false)){
                    val hoe = ItemStack(Material.STONE_HOE)
                    player.inventory.addItem(hoe)
                    player.sendMessage("ばとるのかぎをてにいれた")
                    return
                }else if(line.equals(rast_boss_key,ignoreCase = false)){
                    val hoe = ItemStack(Material.IRON_HOE)
                    player.inventory.addItem(hoe)
                    player.sendMessage("さいごのかぎをてにいれた")
                    return
                }
            }
        }

        //カウントダウン処理

        //木のクワ：迷路ダンジョンへ
        if(event.action.toString().contains("RIGHT_CLICK") && player.inventory.itemInMainHand.type== Material.WOODEN_HOE && !wood_hoe_use && !stone_hoe_use && !iron_hoe_use){
            wood_hoe_use = true
            item(player,use_hoe[0])
        }
        //石のクワ：バトルダンジョンへ
        else if(event.action.toString().contains("RIGHT_CLICK") && player.inventory.itemInMainHand.type== Material.STONE_HOE && !stone_hoe_use && !wood_hoe_use && !iron_hoe_use){
            stone_hoe_use = true
            item(player,use_hoe[1])
        }
        //鉄のクワ：最後のダンジョンへ
        else if(event.action.toString().contains("RIGHT_CLICK") && player.inventory.itemInMainHand.type== Material.IRON_HOE && !iron_hoe_use && !wood_hoe_use && !stone_hoe_use){
            iron_hoe_use = true
            item(player,use_hoe[2])
        }
    }

    private fun sendTitle(remainSeconds: Int){
        Bukkit.getOnlinePlayers().forEach{
            player -> player.sendTitle(remainSeconds.toString(),"",0,15,0)
            player.playSound(player.location, Sound.BLOCK_ANVIL_PLACE,1.0f,1.0f)
        }
    }

    private fun countdown(player: Player,seconds: Int,usehoe: String){

        var remainSeconds = seconds
        object: BukkitRunnable(){
            override fun run(){
                if(0<remainSeconds){
                    sendTitle(remainSeconds)
                    remainSeconds--
                }else{
                    cancel()
                    //ワープ処理
                    //木、石、鉄の種類を判定してどの地点にワープするのか決める
                    teleport_danjon(player,usehoe)
                }
            }
        }.runTaskTimer(plugin,0,20)
    }

    public fun teleport_danjon(player: Player,usehoe: String){
        val world = player.world
        val location = when(usehoe){
            "wood" -> Location(world,227.5,-57.0,11.5)
            "stone" -> Location(world,204.5,-58.0,11.5)
            "iron" -> Location(world,215.5,-57.0,17.5)
            else -> return
        }
        player.teleport(location)
        player.sendMessage("だんじょんにてれぽーとした")
    }

    public fun item(player: Player,usehoe: String){
        val item = player.inventory.itemInMainHand

        if(item.type != Material.AIR){
            player.inventory.setItemInMainHand(null)
        }
        countdown(player,10,usehoe)
    }

    @EventHandler
    fun onPlayerTeleport(event: PlayerTeleportEvent){
        val goalX  = 215
        val goalY = -57
        val goalZ = 11
        val to = event.to ?: return
        if(to.blockX == goalX && to.blockY == goalY && to.blockZ == goalZ){
            wood_hoe_use = false
            stone_hoe_use = false
            iron_hoe_use = false
        }
    }

    var playerLevel = 1 //プレイヤーのレベル
    var EnemyLevel = arrayOf(1,1);//敵のレベル[0]がゾンビ、[1]がスライム
    var nextExp = 10//レベルアップに必要な経験値
    var nowExp = 0//現在の経験値
    var totalExp = 0
    val expcc = 1.5//敵のレベルに応じて取得出来る経験値を補正する値

    @EventHandler
    fun entityKill(event: EntityDeathEvent){
        val killedentity = event.entity
        val killer = killedentity.killer ?: return
        if(killedentity is Zombie){
            expcount(EnemyLevel[0],killer)
        }else if(killedentity is Slime){
            expcount(EnemyLevel[1],killer)
        }
    }

    private fun expcount(enemylev: Int,killer: Player){
        var  lp = expcc.pow(enemylev - 1.0)
        nowExp += ((nextExp*lp)/7).toInt()
        totalExp += ((nextExp*lp)/7).toInt()
        showScoreboard(killer,playerLevel,nowExp,nextExp)
        if(nowExp>=nextExp){
            levelup(killer)
        }
    }

    private fun levelup(killer: Player){
        if(playerLevel<10){
            playerLevel++
        }
        if(EnemyLevel[0]<5){
            EnemyLevel[0]++
        }
        if(EnemyLevel[1]<5){
            EnemyLevel[1]++
        }

        nowExp -= nextExp
        val templp = expcc.pow(playerLevel - 1.0)
        nextExp *= templp.toInt()
        showScoreboard(killer,playerLevel,nowExp,nextExp)
        if(nextExp<=nowExp){
            levelup(killer)
        }
    }

    public fun showScoreboard(player: Player,playerLevel: Int,nowExp: Int,nextExp: Int){
        val manager = Bukkit.getScoreboardManager() ?: return
        val board = manager.newScoreboard
        val objective = board.registerNewObjective("playerStats",Criteria.DUMMY,"${ChatColor.BLACK}${ChatColor.BOLD}ステータス")
        objective.displaySlot = DisplaySlot.SIDEBAR

        val playerlevel = objective.getScore("${ChatColor.GREEN}レベル")
        playerlevel.score = playerLevel
        val next = objective.getScore("次のレベルまであと")
        next.score = nextExp-nowExp
        player.scoreboard = board
    }
}


    public fun gamestart(player: Player){
        //木の剣を手に入れる
        player.sendMessage("まずは木の剣を手に入れよう！")
    }