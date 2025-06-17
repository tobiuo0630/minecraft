package mee.example.untitled1

import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Untitled1 : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        val jsonFile = File(dataFolder,"missions.json")
        if(!jsonFile.exists()){
            logger.warning("missions.jsonが見つかりませんでした")
            return
        }
        val tempMissoin = missionLoad.loadFile(jsonFile)
        server.pluginManager.registerEvents(EventListener(this),this)
        getCommand("mazegen")?.setExecutor(MazegenCommand(this))

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
