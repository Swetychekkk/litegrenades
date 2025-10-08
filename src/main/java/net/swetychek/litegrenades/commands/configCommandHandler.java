package net.swetychek.litegrenades.commands;

import net.swetychek.litegrenades.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class configCommandHandler implements CommandExecutor {
    private final Main plugin;

    public configCommandHandler(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player && !commandSender.isOp()) { commandSender.sendMessage(ChatColor.RED + "No permission"); return true; }

        if (args.length == 0) {
            tellguide(commandSender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "restore":
                if (commandSender instanceof Player) {
                    commandSender.sendMessage(ChatColor.GREEN + "---Task completed---");
                }
                Bukkit.getLogger().info("----Reset config----");
                plugin.getConfig().set("cooldown", 60);
                Bukkit.getLogger().info("cooldown reset");
                plugin.getConfig().set("cooldown_in_creative", false);
                Bukkit.getLogger().info("cooldown_in_creative reset");
                plugin.getConfig().set("explosion_on_collision", false);
                Bukkit.getLogger().info("explosion_on_collision reset");
                plugin.getConfig().set("tnt_timer", 80);
                Bukkit.getLogger().info("tnt_timer reset");
                plugin.getConfig().set("recipes.grenade_tier_one", true);
                Bukkit.getLogger().info("recipes.grenade_tier_one reset");
                plugin.getConfig().set("recipes.grenade_tier_two", true);
                Bukkit.getLogger().info("recipes.grenade_tier_two reset");
                plugin.getConfig().set("rad_grenade.range_modifier", 1.2);
                Bukkit.getLogger().info("recipes.grenade_tier_two reset");
                Bukkit.getLogger().info("Reference created");
                Bukkit.getLogger().info("Saving config...");
                plugin.saveConfig();
                Bukkit.getLogger().info("Saving success");
                Bukkit.getLogger().info("---Task completed---");
                break;
            case "reload":
                Main.getInstance().reloadConfig();
                if (commandSender instanceof Player) {
                    commandSender.sendMessage(ChatColor.YELLOW + "Reloading success");
                }
                Bukkit.getLogger().info("-----Reloading------");
                Bukkit.getLogger().info("Reloading success");
                Bukkit.getLogger().info("---Task completed---");
                break;
            case "help":
                if (!(commandSender instanceof Player)) { return true; }
                tellguide(commandSender);
                break;
            case "give":
                if (!(commandSender instanceof Player player)) { return true; }
                if (args.length >= 2 && args.length <= 3) {
                    try {
                        int number = 1;
                        if (args.length == 3) { number = Integer.parseInt(args[2]); }
                        addGrenade(player, args[1], number);
                        commandSender.sendMessage("Gave " + number + " [" + args[1]+"] to " + ((Player) commandSender).getDisplayName());
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
        } else if (Objects.equals(type, "lightgrenades:radioactive")) {
            //RAD GRENADE
            ItemStack radGrenade = new ItemStack(Material.GREEN_CANDLE, number);
            ItemMeta radGrenadeMeta = radGrenade.getItemMeta();

            NamespacedKey uniqueRadId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            radGrenadeMeta.setDisplayName(ChatColor.GREEN + "Atomic Grenade");
            radGrenadeMeta.getPersistentDataContainer().set(uniqueRadId, PersistentDataType.BOOLEAN, true);
            radGrenade.setItemMeta(radGrenadeMeta);
            player.getInventory().addItem(radGrenade);
        }
    }

    public void tellguide(CommandSender commandSender) {
        commandSender.sendMessage( ChatColor.BLUE + "[Lite" + ChatColor.RED + "Grenades]\n" + ChatColor.WHITE + "Use " + ChatColor.YELLOW +"/litegrenades restore" + ChatColor.WHITE + " to restore default config values\nUse " + ChatColor.YELLOW + "/litegrenades reload" + ChatColor.WHITE + " to reload config file (Excluding craft settings, you need to restart plugin manually)\nUse" + ChatColor.YELLOW + "/litegrenades give {item} {number}" + ChatColor.WHITE + " to give grenades");
    }
}
