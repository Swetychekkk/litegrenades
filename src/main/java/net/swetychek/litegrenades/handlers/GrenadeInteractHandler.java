//3.1 βeta for 1.21.9
//BY SWETYCHEK WITH ❤️
//HELP ME TO MAKE THIS MOD BETTER https://github.com/Swetychekkk/litegrenades/issues

//FILEINFO:
//GRENADE INTERACTION HANDLER

package net.swetychek.litegrenades.handlers;

import net.swetychek.litegrenades.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

//CLASS
public class GrenadeInteractHandler implements Listener {
    private final Main plugin;

    public GrenadeInteractHandler(Main plugin) {
        this.plugin = plugin;
    } //INTRODUCE PLUGIN REFERENCE

    //ENTITY EXPLOSION EVENT HANDLER (CUSTOM RANGE AND EFFECTS)
    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed tnt) {
            NamespacedKey key = new NamespacedKey(plugin, "TYPE");

            //CHECK IF THIS PLUGIN GRENADE
            if (tnt.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                int multiplier = tnt.getPersistentDataContainer().get(key, PersistentDataType.INTEGER); //EXPLOSION RANGE MULTIPLIER
                event.setCancelled(true);
                tnt.getWorld().createExplosion(tnt.getLocation(), 4F * multiplier); //CUSTOM EXPLOSION
                //RADIOACTIVE TNT HANDLER
                if (multiplier == 4) {
                    Location loc = event.getLocation();

                    for (double y = -4; y <= 6; y += 2) {
                        Location cloudLoc = loc.clone().add(0, y, 0);
                        AreaEffectCloud cloud = (AreaEffectCloud) loc.getWorld().spawnEntity(cloudLoc, EntityType.AREA_EFFECT_CLOUD);

                        cloud.setRadius((float) (4.0F*multiplier*plugin.getConfig().getDouble("rad_grenade.range_modifier")));
                        cloud.setDuration(plugin.getConfig().getInt("rad_grenade.cloud_time"));
                        cloud.setColor(Color.GREEN);

                        cloud.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, plugin.getConfig().getInt("rad_grenade.effect_time"), 8), true);
                    }
                }
            }
        }
    }

    //RIGHT CLICK EVENT
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = (Player) event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            ItemMeta itemInMainHand_meta = itemInMainHand.getItemMeta();

            //GETTING CONFIG
            int cooldown = plugin.getConfig().getInt("cooldown");
            boolean cooldown_in_creative = plugin.getConfig().getBoolean("cooldown_in_creative");
            int tnt_timer = plugin.getConfig().getInt("tnt_timer");


            NamespacedKey uniqueId = new NamespacedKey(plugin, "is_tnt_grenade");

            //IF NOT COOLDOWN AND ITEM IN HAND HAS GRENADE FLAG
            if (!player.hasCooldown(itemInMainHand.getType()) && itemInMainHand_meta.getPersistentDataContainer().has(uniqueId, PersistentDataType.BOOLEAN) && itemInMainHand_meta.getPersistentDataContainer().get(uniqueId, PersistentDataType.BOOLEAN).equals(true)) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {event.setCancelled(true);} else {player.swingMainHand();}
                int type = 0;

                //POWER BY COUNT
                switch (itemInMainHand.getType()) {
                    case Material.RED_CANDLE:
                        type=1;
                        break;
                    case  Material.ORANGE_CANDLE:
                        type=3;
                        break;
                    case  Material.GREEN_CANDLE:
                        type = 4;
                }

                //MAKING COOLDOWN
                if (!player.getGameMode().equals(GameMode.CREATIVE) || cooldown_in_creative) {
                    player.setCooldown(itemInMainHand.getType(), cooldown*type); //DYNAMIC COOLDOWN
                    if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    itemInMainHand.setAmount(itemInMainHand.getAmount()-1);}
                }
                Snowball grenade = player.launchProjectile(Snowball.class);

                grenade.setCustomName("tnt_grenade");

                TNTPrimed tnt = player.getWorld().spawn(grenade.getLocation(), TNTPrimed.class);
                NamespacedKey key = new NamespacedKey(plugin, "TYPE"); // уникальный ключ
                tnt.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, type);
                tnt.setFuseTicks(tnt_timer);
                tnt.setSilent(true);

                TextDisplay timer = player.getWorld().spawn(grenade.getLocation(), TextDisplay.class);
                timer.setBillboard(Display.Billboard.CENTER);

                final boolean[] timer_flag = {true};
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if ((!grenade.isValid() || grenade.isDead()) && timer_flag[0]) {
                            boolean explosion_on_collision = plugin.getConfig().getBoolean("explosion_on_collision");
                            if (explosion_on_collision) { tnt.setFuseTicks(0); }
//                            cancel();
//                            return;
                            timer_flag[0] = false;
                        } else if (tnt.isDead()) {
                            grenade.remove();
                            timer.remove();
                            cancel();
                        }
                        tnt.teleport(grenade.getLocation());
                        double left_time = tnt.getFuseTicks()/20.0;
                        timer.teleport(tnt.getLocation().add(0,1.3,0));
                        timer.setText(String.format("%.2f", left_time));
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }
        }
    }

}
