package net.swetychek.litegrenades.commands;

import net.swetychek.litegrenades.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class giveExampleGrenadeCommandExecutor implements CommandExecutor {
    private final Main plugin;

    public giveExampleGrenadeCommandExecutor(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return true; //Handle exceptions

        ItemStack grenadeGive = new ItemStack(Material.RED_CANDLE);
        ItemMeta grenadeMeta= grenadeGive.getItemMeta();

        NamespacedKey uniqueId = new NamespacedKey(plugin, "is_tnt_grenade");
        grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.BOOLEAN, true);
        grenadeGive.setItemMeta(grenadeMeta);
        player.getInventory().addItem(grenadeGive);

        return true;
    }
}
