package com.awokens.voidsurvival.Listeners.Interact;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DirtConvertToMoss implements Listener {

    @EventHandler
    public void moss(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null) return;


        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.getType() != Material.BONE_MEAL) return;

        switch (block.getType()) {
            case DIRT:
                block.setType(Material.MOSS_BLOCK);
                heldItem.subtract(1);
                break;
            case ROOTED_DIRT:
                block.setType(Material.GRASS_BLOCK);
                heldItem.subtract(1);
                break;
            default:
                return;
        }
        block.applyBoneMeal(BlockFace.UP);


    }
}
