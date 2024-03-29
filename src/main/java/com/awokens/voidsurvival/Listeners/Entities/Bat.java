package com.awokens.voidsurvival.Listeners.Entities;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Bat implements Listener {

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Bat)) return;

        event.getDrops().add(new ItemStack(Material.LEATHER));
    }
}
