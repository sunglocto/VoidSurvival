package com.awokens.voidsurvival.Listeners.Interact;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CustomSuspiciousSandDrops implements Listener {

    @EventHandler
    public void brush(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null) return;

        if (!(block.getState() instanceof BrushableBlock brushableBlock)) return;

        ItemStack item = brushableBlock.getItem();

        if (item.getType() != Material.AIR) return;

        LootTable lootTable = Bukkit.getLootTable(LootTables.DESERT_PYRAMID.getKey());

        LootContext lootContext = new LootContext.Builder(block.getLocation())
                .lootedEntity(null)
                .lootingModifier(1)
                .build();

        if (lootTable == null) return;
        Collection<ItemStack> lootItems = lootTable.populateLoot(new Random(), lootContext);

        List<ItemStack> items = new ArrayList<>(lootItems);

        int randomIndex = ThreadLocalRandom.current().nextInt(items.size());
        ItemStack randomItem = items.get(randomIndex);

        brushableBlock.setItem(randomItem);
        brushableBlock.update();
    }
}
