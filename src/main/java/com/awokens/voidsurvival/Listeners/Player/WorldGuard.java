package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.Manager.SpawnPoints;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class WorldGuard implements Listener {

    @EventHandler
    public void target(EntityTargetEvent event) {
        if ((!(event.getTarget() instanceof Player player))) return;
        if (inProtectedRegion(player.getLocation())) event.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (inProtectedRegion(player.getLocation())) event.setCancelled(true);
    }

    @EventHandler
    public void place(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (inProtectedRegion(event.getBlock().getLocation())) event.setCancelled(true);
    }

    @EventHandler
    public void physics(BlockPhysicsEvent event) {
        if (inProtectedRegion(event.getBlock().getLocation())) event.setCancelled(true);
    }


    private enum WorldType {
        WORLD,
        NETHER,
        END
    }

    private boolean inProtectedRegion(Location location) {
        for (WorldType type : WorldType.values()) {
            if (inRegion(location, type)) {
                return true;
            }
        }
        return false;
    }

    private boolean inRegion(Location location, WorldType type) {

        World world = null;

        switch (type) {
            case WORLD -> world = SpawnPoints.getWorldSpawn().getWorld();
            case NETHER -> world = SpawnPoints.getNetherSpawn().getWorld();
            case END -> world = SpawnPoints.getEndSpawn().getWorld();
        }

        if (world == null) return false;

        return inBound(
                location,
                new Location(world, 2.5, -64, 2.5),
                new Location(world, -1.5, -61, -1.5)

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
