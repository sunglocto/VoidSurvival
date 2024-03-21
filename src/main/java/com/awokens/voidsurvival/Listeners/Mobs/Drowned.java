package com.awokens.voidsurvival.Listeners.Mobs;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Drowned implements Listener {

    @EventHandler
    public void spawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Drowned drowned)) return;
        drowned.getEquipment().clear();
    }

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Drowned)) return;
        event.getDrops().removeIf(drop -> drop.getType() == Material.TRIDENT);
    }

}

