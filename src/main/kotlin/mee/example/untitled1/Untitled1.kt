package mee.example.untitled1

import org.bukkit.plugin.java.JavaPlugin

class Untitled1 : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(EventListener(this),this)
        getCommand("mazegen")?.setExecutor(MazegenCommand(this))

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
