package com.awokens.voidsurvival.Listeners.Interact;

import com.awokens.voidsurvival.VoidSurvival;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Date;
import java.util.List;

public class HandTradeSwap implements Listener {

    @EventHandler
    public void trade(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if ((!(event.getRightClicked() instanceof Player clickedPlayer))) return;

        if (!player.isSneaking()) return;
        if (!clickedPlayer.isSneaking()) return;

        Entity target = clickedPlayer.getTargetEntity(3);

        if (target == null || !target.equals(player)) return;

        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemStack clickedPlayerHeldItem = clickedPlayer.getInventory().getItemInMainHand();

        if (heldItem.isEmpty()) return;
        if (clickedPlayerHeldItem.isEmpty()) return;


        MetadataValue value = null;
        if (clickedPlayer.hasMetadata("mutual")) {
            value = clickedPlayer.getMetadata("mutual").get(0);
        }


        if (value == null || value.equals(player)) {
            FixedMetadataValue mutual = new FixedMetadataValue(VoidSurvival.getPlugin(), clickedPlayer);
            FixedMetadataValue timestamp = new FixedMetadataValue(VoidSurvival.getPlugin(), new Date().getTime());
            player.setMetadata("mutual", mutual);
            player.setMetadata("clicked", timestamp);
            return;
        }
        double last = new Date().getTime();
        if (clickedPlayer.hasMetadata("clicked")) {
            last = clickedPlayer.getMetadata("clicked").get(0).asDouble();
        }

        double diff = (new Date().getTime() - last) / 100;
        Bukkit.broadcast(Component.text(diff));

        if (diff <= 0.5) {

            ItemStack first = player.getInventory().getItemInMainHand().clone();
            ItemStack second = clickedPlayer.getInventory().getItemInMainHand().clone();

            player.getInventory().setItemInMainHand(second);
            clickedPlayer.getInventory().setItemInMainHand(first);
            player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.0F);
            clickedPlayer.playSound(clickedPlayer, Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.0F);
        }

        for (String key : List.of("mutual", "clicked")) {
            player.removeMetadata(key, VoidSurvival.getPlugin());
            clickedPlayer.removeMetadata(key, VoidSurvival.getPlugin());
        }
    }
}
