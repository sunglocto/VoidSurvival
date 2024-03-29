package com.awokens.voidsurvival.Listeners.Entities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class WanderingTrader implements Listener {

    @EventHandler
    public void trader(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.WanderingTrader)) return;

        Entity killer = event.getEntity().getKiller();

        if (!(killer instanceof Player player)) return;

        Random random = new Random();
        int lootingLevel = player.getInventory()
                .getItemInMainHand()
                .getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
        int randomValue = random.nextInt(lootingLevel) + 1;


        event.getDrops().add(new ItemStack(Material.EMERALD, randomValue));
    }
}
