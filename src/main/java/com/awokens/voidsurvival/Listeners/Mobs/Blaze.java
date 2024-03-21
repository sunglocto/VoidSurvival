package com.awokens.voidsurvival.Listeners.Mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Blaze implements Listener {

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;

        EntityDamageEvent cause = skeleton.getLastDamageCause();

        if (cause == null) return;
        if (cause.getCause() != EntityDamageEvent.DamageCause.FIRE) return;

        Location location = skeleton.getLocation();
        Location below = location.clone().subtract(0, 1, 0);
        Material type = below.getBlock().getType();

        switch (type) {
            case BLACKSTONE:
            case BLACKSTONE_SLAB:
                break;
            default:
                return;
        }

        event.getDrops().clear();
        location.getWorld().spawnEntity(location, EntityType.BLAZE);

        location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 1);
        location.getWorld().playSound(location, Sound.ENTITY_WITHER_SPAWN, 0.5F, 1.0F);
    }
}
