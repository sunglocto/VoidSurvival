package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.Manager.SpawnPointManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalEnter implements Listener {

    @EventHandler
    public void portal(PlayerPortalEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) return;

        event.setCancelled(true);
        Player player = event.getPlayer();

        switch (player.getWorld().getName()) {
            case "world_nether" -> player.teleport(SpawnPointManager.getNetherSpawn());
            case "world" -> player.teleport(SpawnPointManager.getWorldSpawn());
        }
    }

}
