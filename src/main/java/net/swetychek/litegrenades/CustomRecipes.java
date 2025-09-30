package net.swetychek.litegrenades;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomRecipes {
    public static void register(JavaPlugin plugin) {
        if (plugin.getConfig().getBoolean("recipes.grenade_tier_one", true)) {
            //GRENADE TIER ONE
            ItemStack grenadeItem = new ItemStack(Material.RED_CANDLE);
            ItemMeta grenadeMeta = grenadeItem.getItemMeta();

            NamespacedKey uniqueId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            grenadeMeta.setDisplayName(ChatColor.RED + "TNT");
            grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.BOOLEAN, true);
            grenadeItem.setItemMeta(grenadeMeta);

            ShapelessRecipe grenadeRecipe = new ShapelessRecipe(new NamespacedKey(Main.getInstance(), "Grenade-Recipe"), grenadeItem);
            grenadeRecipe.addIngredient(1, Material.TNT);
            grenadeRecipe.addIngredient(1, Material.CANDLE);
            Bukkit.addRecipe(grenadeRecipe);
        }
        if (plugin.getConfig().getBoolean("recipes.grenade_tier_two", true)) {
            //GRENADE TIER TWO
            ItemStack grenadeTwo = new ItemStack(Material.ORANGE_CANDLE);
            ItemMeta grenadeTwoMeta= grenadeTwo.getItemMeta();

            NamespacedKey uniquetId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            grenadeTwoMeta.setDisplayName(ChatColor.GOLD + "TNT TierTwo");
            grenadeTwoMeta.getPersistentDataContainer().set(uniquetId, PersistentDataType.BOOLEAN, true);
            grenadeTwo.setItemMeta(grenadeTwoMeta);

            ShapelessRecipe grenadeTwoRecipe = new ShapelessRecipe(new NamespacedKey(Main.getInstance(), "Grenade-Two-Recipe"), grenadeTwo);
            grenadeTwoRecipe.addIngredient(3, Material.TNT);
            grenadeTwoRecipe.addIngredient(1, Material.CANDLE);
            Bukkit.addRecipe(grenadeTwoRecipe);
        }
    }
}
