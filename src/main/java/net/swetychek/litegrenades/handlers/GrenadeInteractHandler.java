package net.swetychek.litegrenades.handlers;

import net.swetychek.litegrenades.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class GrenadeInteractHandler implements Listener {
    private final Main plugin;

    public GrenadeInteractHandler(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = (Player) event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            ItemMeta itemInMainHand_meta = itemInMainHand.getItemMeta();
            int cooldown = plugin.getConfig().getInt("cooldown");
            boolean cooldown_in_creative = plugin.getConfig().getBoolean("cooldown_in_creative");
            int tnt_timer = plugin.getConfig().getInt("tnt_timer");


            NamespacedKey uniqueId = new NamespacedKey(plugin, "is_tnt_grenade");

            if (itemInMainHand.getType().equals(Material.RED_CANDLE) && !player.hasCooldown(itemInMainHand.getType()) && itemInMainHand_meta.getPersistentDataContainer().has(uniqueId, PersistentDataType.BOOLEAN) && itemInMainHand_meta.getPersistentDataContainer().get(uniqueId, PersistentDataType.BOOLEAN).equals(true)) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {event.setCancelled(true);} else {player.swingMainHand();}
                if (!player.getGameMode().equals(GameMode.CREATIVE) || cooldown_in_creative) {
                    player.setCooldown(itemInMainHand.getType(), cooldown);
                    if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    itemInMainHand.setAmount(itemInMainHand.getAmount()-1);}
                }
                Snowball grenade = player.launchProjectile(Snowball.class);

                grenade.setCustomName("tnt_grenade");

                TNTPrimed tnt = player.getWorld().spawn(grenade.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(tnt_timer);
                tnt.setSilent(true);

                // Трекер
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!grenade.isValid() || grenade.isDead()) {
                            boolean explosion_on_collision = plugin.getConfig().getBoolean("explosion_on_collision");
                            if (explosion_on_collision) { tnt.setFuseTicks(0); }
                            cancel();
                            return;
                        } else if (tnt.isDead()) {
                            grenade.remove();
                        }

                        tnt.teleport(grenade.getLocation());
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }
        }
    }

}
