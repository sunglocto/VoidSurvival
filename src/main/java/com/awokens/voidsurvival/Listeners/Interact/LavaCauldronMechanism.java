//package com.awokens.voidsurvival.Listeners.Interact;
//
//import org.bukkit.Material;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//import org.bukkit.block.Campfire;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerInteractEvent;
//
//public class LavaCauldronMechanism implements Listener {
//
//    @EventHandler
//    public void interact(PlayerInteractEvent event) {
//
//        Block clickdBlock = event.getClickedBlock();
//
//        if (clickdBlock == null || clickdBlock.getType() != Material.CAULDRON) return;
//
//        // 	set {_under} to block below clicked block
//        //	if {_under} isn't a campfire:
//        //		exit
//        //	if block data tag "lit" of {_under} isn't true:
//        //		exit
//        //	play sound "minecraft:item.bundle.insert" at clicked block
//        //	play sound "minecraft:block.grindstone.use" at pitch 0.5 at clicked block
//        //	remove held item from player's held item
//
//        Block below = clickdBlock.getRelative(BlockFace.DOWN);
//
//        if (below.getType() != Material.CAMPFIRE) return;
//
//        Campfire campfire = (Campfire) below;
//
//        campfire.get
//
//    }
//}
