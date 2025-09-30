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

import java.util.Objects;

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
                Main.getInstance().getConfig().set("recipes.grenade_tier_one", true);
                Main.getInstance().getConfig().set("recipes.grenade_tier_two", true);
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
                if (args.length == 3) {
                    try {
                        int number = Integer.parseInt(args[2]);
                        addGrenade(player, args[1], number);
                        return true;
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Second argument must be instanceof INTEGER");
                    }
                }
//                } else {
//                    addGrenade(player, 1);
//                }

                break;
        }
        return true;
    }

    public void addGrenade(Player player, String type, int number) {
        if (Objects.equals(type, "lightgrenades:grenade")) {
            ItemStack grenadeItem = new ItemStack(Material.RED_CANDLE, number);
            ItemMeta grenadeMeta= grenadeItem.getItemMeta();

            NamespacedKey uniqueId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            grenadeMeta.setDisplayName(ChatColor.RED + "TNT");
            grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.BOOLEAN, true);
            grenadeItem.setItemMeta(grenadeMeta);
            player.getInventory().addItem(grenadeItem);
        } else if (Objects.equals(type, "lightgrenades:tier_two")) {
            ItemStack grenadeItem = new ItemStack(Material.ORANGE_CANDLE, number);
            ItemMeta grenadeMeta= grenadeItem.getItemMeta();

            NamespacedKey uniqueId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            grenadeMeta.setDisplayName(ChatColor.GOLD + "TNT TierTwo");
            grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.BOOLEAN, true);
            grenadeItem.setItemMeta(grenadeMeta);
            player.getInventory().addItem(grenadeItem);
        }
    }

    public void tellguide(CommandSender commandSender) {
        commandSender.sendMessage( ChatColor.BLUE + "[Lite" + ChatColor.RED + "Grenades]\n" + ChatColor.WHITE + "Use " + ChatColor.YELLOW +"/litegrenades restore" + ChatColor.WHITE + " to restore default config values\nUse " + ChatColor.YELLOW + "/litegrenades reload" + ChatColor.WHITE + " to reload config file (Excluding craft settings, you need to restart plugin manually)\nUse" + ChatColor.YELLOW + "/litegrenades give {item} {number}" + ChatColor.WHITE + " to give grenades");
    }
}
