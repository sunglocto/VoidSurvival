package com.awokens.voidsurvival.Manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TNTTrailManager {

    // blocks that connected between other related blocks
    // if obsidian block is in between a -> b. Then we cannot
    // reach b as there is a correlative block, which is the obsidian block.
    // But any other surrounding blocks can be reached.
    private final Set<Location> relatives;

    private int counter;

    private final Plugin plugin;


    private final int max_counter = 100;

    public TNTTrailManager(Plugin plugin, Block startBlock, int counter) {
        this.relatives = new HashSet<>();
        this.plugin = plugin;
        this.counter = Math.min(counter, max_counter);
        trail(startBlock);

    }

    public static List<Block> getConnectedBlocks(Block next) {
        List<Block> neighbors = new ArrayList<>();

        // Get all 6 blocks that are neighbors of the next block
        neighbors.add(next.getRelative(BlockFace.NORTH));
        neighbors.add(next.getRelative(BlockFace.SOUTH));
        neighbors.add(next.getRelative(BlockFace.WEST));
        neighbors.add(next.getRelative(BlockFace.EAST));
        neighbors.add(next.getRelative(BlockFace.UP));
        neighbors.add(next.getRelative(BlockFace.DOWN));
        return neighbors;
    }


    public static boolean isRelative(Block block) {
        if (!block.getType().isSolid()) return false;
        double hardness = block.getType().getHardness();

        return switch (block.getType()) {
            case TUFF, COBBLED_DEEPSLATE -> true;
            default -> !(hardness >= 3.0F) && !(hardness < 0.3F);
        };

    }

    private void trail(Block next) {

        for (Block connectedBlock : getConnectedBlocks(next)) {

            if (this.counter < 0 || this.counter > max_counter) break;

            if (!isRelative(connectedBlock)) continue;

            Location location = connectedBlock.getLocation();

            if (this.relatives.contains(location)) continue;

            int tier = switch (connectedBlock.getType()) {
                case TUFF -> 2;
                case COBBLED_DEEPSLATE -> 3;
                default -> 1;
            };

            if (this.counter - tier < 0) return;

            connectedBlock.breakNaturally(true);

            location.getWorld().playSound(
                    location, Sound.BLOCK_GRAVEL_BREAK, 0.5F, 0.5F);
            location.getWorld().playSound(
                    location, Sound.ITEM_DYE_USE, 0.5F, 0.5F);

            this.relatives.add(location);

            this.counter -= tier;

            new BukkitRunnable() {
                @Override
                public void run() {
                    trail(connectedBlock);
                }
            }.runTaskLater(plugin, 5L);
        }
    }
}
