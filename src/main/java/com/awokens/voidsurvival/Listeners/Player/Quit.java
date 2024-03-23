package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.VoidSurvival;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {

    @EventHandler
    public void quit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (VoidSurvival.getCollisionTeam().hasPlayer(player)) {
            VoidSurvival.getCollisionTeam().removePlayer(player);
        }

        VoidSurvival.getMapResetScheduler().getMapResetBar().removePlayer(player);

        Entity vehicle = player.getVehicle();

        if (vehicle == null) return;

        vehicle.eject();
    }
}
