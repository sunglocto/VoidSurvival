package com.awokens.voidsurvival.Listeners.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class Fishing implements Listener {

    @EventHandler
    public void fish(PlayerFishEvent event) {

        Player player = event.getPlayer();
        PlayerFishEvent.State state = event.getState();

        if (state == PlayerFishEvent.State.BITE) {
            player.playSound(player, Sound.ENTITY_PLAYER_SPLASH, 0.5F, 1.0F);

            Title title = Title.title(
                    Component.text(""),
                    MiniMessage.miniMessage().deserialize("<bold><gradient:#1A75D3:#1C87F6:#1A75D3>\uD83C\uDFA3  BITE!")
            );
            player.showTitle(title);
            return;
        }

        if (state != PlayerFishEvent.State.CAUGHT_FISH) return;

        Location head = player.getEyeLocation();
        Vector direction = head.getDirection();
        Location front = head.clone().add(direction.multiply(1));

        player.spawnParticle(Particle.SONIC_BOOM, front, 1);

    }
//
//
//    show sonic boom 1 block infront of player's head
//    set metadata tag "fishing" of last spawned xp to true
//            if chance of 1%:
//    set {_caught} to caught fish
//    spawn elder guardian or guardian at fishing caught entity
//    set (last spawned entity)'s held item to item of {_caught}
//    set item of {_caught} to air
//    show huge explosion at fishing caught entity
//    play sound "minecraft:entity.elder_guardian.curse" at fishing caught entity
}
