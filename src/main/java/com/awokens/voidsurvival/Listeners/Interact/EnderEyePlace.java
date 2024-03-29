package com.awokens.voidsurvival.Listeners.Interact;

import com.awokens.voidsurvival.Manager.SpawnPointManager;
import com.awokens.voidsurvival.VoidSurvival;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderEyePlace implements Listener {

    private final VoidSurvival plugin;
    public EnderEyePlace(VoidSurvival plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void eyePlace(BlockPlaceEvent event) {

        Block block = event.getBlock();

        if (block.getType() != Material.END_PORTAL_FRAME) return;

        EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();

        if (!portalFrame.hasEye()) return;

        Player player = event.getPlayer();

        if (player.getCooldown(Material.ENDER_EYE) > 0) {
            event.setCancelled(true);
            return;
        }

        player.setCooldown(Material.ENDER_EYE, 20 * 10);


        player.addPotionEffect(
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 5, false, false, false)
        );
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1)
        );
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.CONFUSION, 20 * 10, 1, false, false, false)
        );

        Location init = player.getLocation().clone();

        player.playSound(init, Sound.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F);

        player.setPortalCooldown(20 * 10);

        Location whereTo = switch (player.getWorld().getName()) {
            case "world_the_end" -> SpawnPointManager.getWorldSpawn();
            default -> SpawnPointManager.getEndSpawn();
        };

        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleportAsync(whereTo).thenRun(() -> {
                    for (Player online : plugin.getServer().getOnlinePlayers()) {
                        online.playSound(online, Sound.BLOCK_END_PORTAL_SPAWN, 1.0F, 0.5F);
                    }
                    block.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, block.getLocation(), 1);
                    block.getLocation().getWorld().createExplosion(block.getLocation(), 3);
                    block.breakNaturally(true);
                    player.playSound(player, Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 0.5F);
                    player.playSound(init, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.5F);
                });
            }
        }.runTaskLater(plugin, 20L * 4L);

    }
}
