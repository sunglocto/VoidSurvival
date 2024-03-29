package com.awokens.voidsurvival.Listeners.Entities;

import com.awokens.voidsurvival.Manager.SpawnPointManager;
import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class Piglin implements Listener {

    @EventHandler
    public void spawn(PreCreatureSpawnEvent event) {

        EntityType type = event.getType();

        if (type != EntityType.PIGLIN) return;

        Location location = event.getSpawnLocation();

        if (!location.getWorld().getName().equalsIgnoreCase(SpawnPointManager.getNetherSpawn().getWorld().getName())) return;

        if (event.getReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;

        event.setCancelled(true);
    }

}
