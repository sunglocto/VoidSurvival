package com.awokens.voidsurvival.Listeners.Entities;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class GodVillager implements Listener {

    @EventHandler
    public void career(VillagerCareerChangeEvent event) {
        switch (event.getProfession()) {
            case ARMORER:
            case LIBRARIAN:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof org.bukkit.entity.Villager villager)) return;
        switch (villager.getProfession()) {
            case ARMORER:
            case LIBRARIAN:
                event.setCancelled(true);
                villager.setProfession(org.bukkit.entity.Villager.Profession.NONE);
        }
    }

    @EventHandler
    public void spawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof ZombieVillager villager)) return;
        villager.setVillagerProfession(org.bukkit.entity.Villager.Profession.NONE);
    }

    @EventHandler
    public void cure(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked() instanceof ZombieVillager villager)) return;

        org.bukkit.entity.Villager.Profession profession = villager.getVillagerProfession();

        if (profession != org.bukkit.entity.Villager.Profession.NITWIT) return;

        if (!villager.hasPotionEffect(PotionEffectType.WEAKNESS)) return;

        if (villager.isConverting()) return;

        Player player = event.getPlayer();

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.isEmpty()) return;

        if (heldItem.getType() != Material.ENCHANTED_GOLDEN_APPLE) return;

        player.swingMainHand();
        heldItem.subtract(1);

        Random rand = new Random();

        int oxidizedCopper = rand.nextInt(50 - 20 + 1) + 20;
        int phantomMembrane = rand.nextInt(50 - 20 + 1) + 20;
        int nautilusShell = rand.nextInt(32 - 16 + 1) + 16;
        int emeraldBlock = rand.nextInt(32 - 16 + 1) + 16;

        String nbtString = "{VillagerData:{type:plains,profession:nitwit,level:99},Offers:{Recipes:["
                + "{maxUses:1,buy:{id:oxidized_copper,Count:" + oxidizedCopper
                + "},buyB:{id:heart_of_the_sea,Count:1},sell:{id:trident,Count:1}},"
                + "{maxUses:8,buy:{id:phantom_membrane,Count:" + phantomMembrane
                + "},buyB:{id:nautilus_shell,Count:" + nautilusShell
                + "},sell:{id:shulker_shell,Count:1}},"
                + "{maxUses:4,buy:{id:emerald_block,Count:" + emeraldBlock
                + "},buyB:{id:book,Count:1},sell:{id:enchanted_book,Count:1,tag:{StoredEnchantments:[{id:\"minecraft:swift_sneak\",lvl:3s}]}}}"
                + "]}}";

        NBTContainer container = new NBTContainer(nbtString);

        NBTEntity entity = new NBTEntity(villager);

        entity.mergeCompound(container);

        villager.customName(MiniMessage.miniMessage().deserialize(
                "<dark_gray>God Villager</dark_gray>"
        ).decoration(TextDecoration.ITALIC, false));

        int min = 20;
        int max = 100;
        int time = rand.nextInt((max - min) + 1) + min;
        villager.setConversionTime(time);

    }
}
