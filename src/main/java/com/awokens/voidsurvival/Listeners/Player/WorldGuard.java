package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.Manager.SpawnPointManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class WorldGuard implements Listener {

    @EventHandler
    public void target(EntityTargetEvent event) {
        if ((!(event.getTarget() instanceof Player player))) return;
        if (inProtectedRegion(player.getLocation())) event.setCancelled(true);
    }

    @EventHandler
    public void self(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (inProtectedRegion(player.getLocation())) event.setCancelled(true);
    }

    @EventHandler
    public void other(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player attacker)) return;

        if (inProtectedRegion(victim.getLocation())) event.setCancelled(true);
        if (inProtectedRegion(attacker.getLocation())) event.setCancelled(true);
    }

    @EventHandler
    public void place(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (inProtectedRegion(event.getBlock().getLocation())) event.setCancelled(true);
    }

    @EventHandler
    public void physics(BlockPhysicsEvent event) {
        if (!inProtectedRegion(event.getBlock().getLocation())) return;

        if (!event.getBlock().isLiquid()) return;

        event.setCancelled(true);
        event.getSourceBlock().setType(Material.AIR);

    }

    @EventHandler
    public void fertilize(BlockFertilizeEvent event) {
        if (inProtectedRegion(event.getBlock().getLocation())) event.setCancelled(true);
    }

    private enum WorldType {
        WORLD,
        NETHER,
        END
    }

    private boolean inProtectedRegion(Location location) {

        Location spawn = switch (location.getWorld().getName()) {
            case "world" -> SpawnPointManager.getWorldSpawn();
            case "world_nether" -> SpawnPointManager.getNetherSpawn();
            case "world_the_end" -> SpawnPointManager.getEndSpawn();
            default -> throw new IllegalStateException("Unexpected value: " + location.getWorld().getName());
        };

        return inBound(
                location,
                new Location(spawn.getWorld(), 2.5,  spawn.getY() - 1, 2.5),
                new Location(spawn.getWorld(), -2, spawn.getY() + 3, -2)

        );
    }

    private boolean inBound(Location location, Location loc1, Location loc2) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double x1 = Math.min(loc1.getX(), loc2.getX());
        double y1 = Math.min(loc1.getY(), loc2.getY());
        double z1 = Math.min(loc1.getZ(), loc2.getZ());

        double x2 = Math.max(loc1.getX(), loc2.getX());
        double y2 = Math.max(loc1.getY(), loc2.getY());
        double z2 = Math.max(loc1.getZ(), loc2.getZ());

        return x >= x1 && y >= y1 && z >= z1 && x <= x2 && y <= y2 && z <= z2;
    }

}
