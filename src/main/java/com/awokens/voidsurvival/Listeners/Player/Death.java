package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.Manager.SpawnPointManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Death implements Listener {

    @EventHandler
    public void death(PlayerDeathEvent event) {
        event.deathMessage(Component.text(""));

        Player victim = event.getPlayer();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(MiniMessage.miniMessage().deserialize(
                    "<gradient:#84D0FC:#ABDDFA:#84D0FC>" + victim.getName() + " has died"
            ));
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (!event.isBedSpawn()) {
            event.setRespawnLocation(SpawnPointManager.getWorldSpawn());
        }

        Location respawn = event.getRespawnLocation();

        player.setVelocity(new Vector());
        player.teleportAsync(respawn).thenRun(new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5, 1, false, false, false));
            }
        });
    }
}
