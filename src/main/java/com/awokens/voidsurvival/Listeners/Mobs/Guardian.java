package com.awokens.voidsurvival.Listeners.Mobs;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Guardian implements Listener {

    @EventHandler
    public void elder(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof ElderGuardian)) return;

        Entity killer = event.getEntity().getKiller();

        if (!(killer instanceof Player player)) return;

        Random random = new Random();
        int lootingLevel = player.getInventory()
                .getItemInMainHand().
                getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
        double chance = 15 + (11.667 * lootingLevel);
        double randomValue = random.nextDouble() * 100;

        if (randomValue <= chance) {
            event.getDrops().add(new ItemStack(Material.HEART_OF_THE_SEA, 1));
        }

        if (!player.hasPotionEffect(PotionEffectType.LUCK)) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, (20 * 60 * 4), 2));
    }

    @EventHandler
    public void guardian(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Guardian)) return;

        Entity killer = event.getEntity().getKiller();

        if (!(killer instanceof Player player)) return;

        if (player.hasPotionEffect(PotionEffectType.LUCK)) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, (20 *60 * 4), 2));
    }

}
