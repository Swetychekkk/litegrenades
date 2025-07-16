package net.swetychek.litegrenades;


import net.swetychek.litegrenades.commands.giveExampleGrenadeCommandExecutor;
import net.swetychek.litegrenades.handlers.GrenadeInteractHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getLogger().info("[Lite-Grenades]: Plugin Enabled");
        getServer().getPluginManager().registerEvents(new GrenadeInteractHandler(this), this);
        CustomRecipes.register();
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("[Lite-Grenades]: Plugin disabled");
    }

    //S-TONE
    public static Main getInstance() {
        return getPlugin(Main.class);
    }
}