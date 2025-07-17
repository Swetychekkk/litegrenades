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
        if (commandSender instanceof Player && !commandSender.isOp()) { commandSender.sendMessage(ChatColor.RED + "No permission"); return true; }

        if (args.length == 0) {
            tellguide(commandSender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "restore":
                Main.getInstance().getConfig().set("cooldown", 60);
                Main.getInstance().getConfig().set("cooldown_in_creative", false);
                Main.getInstance().getConfig().set("explosion_on_collision", false);
                Main.getInstance().getConfig().set("tnt_timer", 80);
                Main.getInstance().saveConfig();
                break;
            case "reload":
                Main.getInstance().reloadConfig();
                break;
            case "help":
                if (!(commandSender instanceof Player)) { return true; }
                tellguide(commandSender);
                break;
            case "give":
                if (!(commandSender instanceof Player player)) { return true; }
                if (args.length == 2) {
                    try {
                        int number = Integer.parseInt(args[1]);
                        addGrenade(player, number);
                        return true;
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Second argument must be instanceof INTEGER");
                    }
                } else {
                    addGrenade(player, 1);
                }

                break;
        }
        return true;
    }

    public void addGrenade(Player player, int number) {
        ItemStack grenadeItem = new ItemStack(Material.RED_CANDLE, number);
        ItemMeta grenadeMeta= grenadeItem.getItemMeta();

        NamespacedKey uniqueId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
        grenadeMeta.setDisplayName(ChatColor.RED + "TNT");
        grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.BOOLEAN, true);
        grenadeItem.setItemMeta(grenadeMeta);
        player.getInventory().addItem(grenadeItem);
    }

    public void tellguide(CommandSender commandSender) {
        commandSender.sendMessage( ChatColor.BLUE + "[Lite" + ChatColor.RED + "Grenades]\n" + ChatColor.WHITE + "Use " + ChatColor.YELLOW +"/litegrenades restore" + ChatColor.WHITE + " to restore default config values\nUse " + ChatColor.YELLOW + "/litegrenades reload" + ChatColor.WHITE + " to reload config file\nUse" + ChatColor.YELLOW + "/litegrenades give {number}" + ChatColor.WHITE + " to reload config file");
    }
}
