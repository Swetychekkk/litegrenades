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

public class CustomRecipes {
    public static void register() {
        ItemStack grenadeItem = new ItemStack(Material.RED_CANDLE);
        ItemMeta grenadeMeta= grenadeItem.getItemMeta();

        NamespacedKey uniqueId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
        grenadeMeta.setDisplayName(ChatColor.RED + "TNT");
        grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.STRING, "true");
        grenadeItem.setItemMeta(grenadeMeta);

        ShapelessRecipe grenadeRecipe = new ShapelessRecipe(new NamespacedKey(Main.getInstance(), "Grenade-Recipe"), grenadeItem);
        grenadeRecipe.addIngredient(1, Material.TNT);
        grenadeRecipe.addIngredient(1, Material.CANDLE);
        Bukkit.addRecipe(grenadeRecipe);
    }
}
