package net.swetychek.litegrenades.commands;

import net.swetychek.litegrenades.Main;
import org.bukkit.ChatColor;
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

public class configCommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 0) {
            commandSender.sendMessage( ChatColor.BLUE + "[Lite" + ChatColor.RED + "Grenades] " + ChatColor.WHITE + "Use /litegrenades restore to restore default config values /litegrenades reload to reload config file");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "restore":
                Main.getInstance().getConfig().set("cooldown", 60);
                Main.getInstance().getConfig().set("cooldown_in_creative", false);
                Main.getInstance().getConfig().set("explosion_on_collision", false);
                Main.getInstance().getConfig().set("tnt_timer", 80);
                break;
            case "reload":
                Main.getInstance().reloadConfig();
                break;
            case "help":
                if (!(commandSender instanceof Player)) { return true; }
                commandSender.sendMessage( ChatColor.BLUE + "[Lite" + ChatColor.RED + "Grenades] " + ChatColor.WHITE + "Use /litegrenades restore to restore default config values /litegrenades reload to reload config file");
                break;
            case "give":
                if (!(commandSender instanceof Player player)) { return true; }
                ItemStack grenadeItem = new ItemStack(Material.RED_CANDLE);
                ItemMeta grenadeMeta= grenadeItem.getItemMeta();

                NamespacedKey uniqueId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
                grenadeMeta.setDisplayName(ChatColor.RED + "TNT");
                grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.BOOLEAN, true);
                grenadeItem.setItemMeta(grenadeMeta);
                player.getInventory().addItem(grenadeItem);
                break;
        }
        return true;
    }
}
