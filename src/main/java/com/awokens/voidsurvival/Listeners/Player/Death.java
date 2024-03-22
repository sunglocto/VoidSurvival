package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.Manager.SpawnPointManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
        if (!event.isBedSpawn()) {
            event.setRespawnLocation(SpawnPointManager.getWorldSpawn());
        }
    }
}
