package net.swetychek.litegrenades;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomRecipes {
    private final Main plugin;
    public CustomRecipes(Main plugin) { this.plugin = plugin; }

    public static void register(JavaPlugin plugin) {
            //GRENADE TIER ONE
            ItemStack grenadeItem = new ItemStack(Material.RED_CANDLE);
            ItemMeta grenadeMeta = grenadeItem.getItemMeta();

            NamespacedKey uniqueId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            grenadeMeta.setDisplayName(ChatColor.RED + "TNT");
            grenadeMeta.getPersistentDataContainer().set(uniqueId, PersistentDataType.BOOLEAN, true);
            grenadeItem.setItemMeta(grenadeMeta);

            ShapelessRecipe grenadeRecipe = new ShapelessRecipe(new NamespacedKey(Main.getInstance(), "Grenade-Recipe"), grenadeItem);
//            grenadeRecipe.addIngredient(1, Material.TNT);
//            grenadeRecipe.addIngredient(1, Material.CANDLE);
            loadRecipeIngredients("grenade_tier_one", grenadeRecipe, plugin);
            Bukkit.addRecipe(grenadeRecipe);


            //GRENADE TIER TWO
            ItemStack grenadeTwo = new ItemStack(Material.ORANGE_CANDLE);
            ItemMeta grenadeTwoMeta= grenadeTwo.getItemMeta();

            NamespacedKey uniquetId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            grenadeTwoMeta.setDisplayName(ChatColor.GOLD + "TNT TierTwo");
            grenadeTwoMeta.getPersistentDataContainer().set(uniquetId, PersistentDataType.BOOLEAN, true);
            grenadeTwo.setItemMeta(grenadeTwoMeta);

            ShapelessRecipe grenadeTwoRecipe = new ShapelessRecipe(new NamespacedKey(Main.getInstance(), "Grenade-Two-Recipe"), grenadeTwo);
//            grenadeTwoRecipe.addIngredient(3, Material.TNT);
//            grenadeTwoRecipe.addIngredient(1, Material.CANDLE);
            loadRecipeIngredients("grenade_tier_two", grenadeTwoRecipe, plugin);
            Bukkit.addRecipe(grenadeTwoRecipe);


            //RAD GRENADE
            ItemStack radGrenade = new ItemStack(Material.GREEN_CANDLE);
            ItemMeta radGrenadeMeta = radGrenade.getItemMeta();

            NamespacedKey uniqueRadId = new NamespacedKey(Main.getInstance(), "is_tnt_grenade");
            radGrenadeMeta.setDisplayName(ChatColor.GREEN + "Atomic Grenade");
            radGrenadeMeta.getPersistentDataContainer().set(uniqueRadId, PersistentDataType.BOOLEAN, true);
            radGrenade.setItemMeta(radGrenadeMeta);

            ShapelessRecipe grenadeRadRecipe = new ShapelessRecipe(new NamespacedKey(Main.getInstance(), "Grenade-Atomic-Recipe"), radGrenade);
//            grenadeRadRecipe.addIngredient(4, Material.TNT);
//            grenadeRadRecipe.addIngredient(1, Material.CANDLE);
//            grenadeRadRecipe.addIngredient(1, Material.POISONOUS_POTATO);
//            grenadeRadRecipe.addIngredient(2, Material.ROTTEN_FLESH);
//            grenadeRadRecipe.addIngredient(1, Material.LINGERING_POTION);
            loadRecipeIngredients("radioactive_grenade", grenadeRadRecipe, plugin);
            Bukkit.addRecipe(grenadeRadRecipe);
    }
        private static void loadRecipeIngredients(String recipeName, ShapelessRecipe recipe, JavaPlugin plugin) {
                ConfigurationSection section = plugin.getConfig().getConfigurationSection("crafts." + recipeName);
                if (section == null) {
                        plugin.getLogger().warning("NOT VALID SECTION " + recipeName);
                        return;
                }

                for (String matName : section.getKeys(false)) {
                        Object rawValue = section.get(matName);
                        int amount = (rawValue instanceof Number) ? ((Number) rawValue).intValue() : 1;

                        Material mat = Material.matchMaterial(matName);
                        if (mat == null) {
                                plugin.getLogger().warning("ERR: MATERIAL NOT FOUND '" + recipeName + "': " + matName);
                                continue;
                        }

                        for (int i = 0; i < amount; i++) {
                                recipe.addIngredient(mat);
                        }
                }
        }

}
