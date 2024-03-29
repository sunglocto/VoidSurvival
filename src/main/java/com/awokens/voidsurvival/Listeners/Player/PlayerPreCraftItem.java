package com.awokens.voidsurvival.Listeners.Player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPreCraftItem implements Listener {
    @EventHandler
    public void ender(PrepareItemCraftEvent event) {

        if (event.getRecipe() == null) return;

        if (event.getRecipe().getResult().getType() != Material.ENDER_EYE) return;

        for (ItemStack matrix : event.getInventory().getMatrix()) {
            if (matrix == null) continue;
            if (matrix.isEmpty()) continue;

            if (matrix.getType() == Material.HEART_OF_THE_SEA) return;
        }

        event.getInventory().setResult(new ItemStack(Material.AIR));
    }
}
