package com.awokens.voidsurvival.Listeners.Interact;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class SandMechanism implements Listener {

    @EventHandler
    public void consume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        Block block = player.getTargetBlockExact(4);

        if (block == null) return;

        switch (block.getType()) {
            case CLAY, MUD -> event.setCancelled(true);
        }

    }

    @EventHandler
    public void dirt(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null || block.getType() != Material.DIRT) return;

        ItemStack used = event.getItem();

        if (used == null || used.isEmpty()) return;

    }
}
