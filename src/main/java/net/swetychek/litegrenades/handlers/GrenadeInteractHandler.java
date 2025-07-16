package net.swetychek.litegrenades.handlers;

import net.swetychek.litegrenades.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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


            NamespacedKey uniqueId = new NamespacedKey(plugin, "is_tnt_grenade");

            if (itemInMainHand.getType().equals(Material.RED_CANDLE) && !player.hasCooldown(itemInMainHand) && itemInMainHand_meta.getPersistentDataContainer().has(uniqueId, PersistentDataType.BOOLEAN) && itemInMainHand_meta.getPersistentDataContainer().get(uniqueId, PersistentDataType.BOOLEAN).equals(Boolean.TRUE)) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {event.setCancelled(true);} else {player.swingMainHand();}
                if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    player.setCooldown(itemInMainHand, 60);
                    itemInMainHand.setAmount(itemInMainHand.getAmount()-1);
                }
                Snowball grenade = player.launchProjectile(Snowball.class);

                grenade.setCustomName("tnt_grenade");

                TNTPrimed tnt = player.getWorld().spawn(grenade.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(80);
                tnt.setSilent(true);

                // Трекер
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!grenade.isValid() || grenade.isDead()) {
//                            tnt.setFuseTicks(0);
                            cancel();
                            return;
                        }

                        tnt.teleport(grenade.getLocation());
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }
        }
    }

}
