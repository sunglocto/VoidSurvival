package com.awokens.voidsurvival.Listeners.Player;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerArmorEquip implements Listener {

    @EventHandler
    public void equip(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();

        int counter = 0;

        ItemStack newItem = event.getNewItem().clone();
        ItemStack olditem = event.getOldItem().clone();

        if (isDiamondArmorPiece(olditem) && player.getItemOnCursor().isSimilar(olditem) && isDiamondArmorPiece(newItem)) return;

        for (ItemStack armorContent : player.getInventory().getArmorContents()) {

            if (armorContent == null) continue;

            if (isDiamondArmorPiece(armorContent) || player.getCooldown(armorContent.getType()) > 0) {
                counter += 1;
            }
        }

        if (counter <= 1) return; // good to wear regardless

        player.getInventory().remove(newItem);
        player.getInventory().remove(olditem);
        int slot = switch (event.getSlotType()) {
            case HEAD -> 39;
            case CHEST -> 38;
            case LEGS -> 37;
            case FEET -> 36;
        };
        player.getInventory().setItem(slot, olditem);
        player.getInventory().addItem(newItem);

        player.setCooldown(olditem.getType(), 20);
        player.setCooldown(newItem.getType(), 20);
        player.updateInventory();
    }

    @EventHandler
    public void click(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack clickedItem = switch (event.getClick()) {
            case NUMBER_KEY -> player.getInventory().getItem(event.getHotbarButton());
            default -> event.getCurrentItem();
        };

        if (clickedItem == null) return;

        if (!isDiamondArmorPiece(clickedItem)) return;

        if (player.getCooldown(clickedItem.getType()) <= 0) return;

        event.setCancelled(true);
        player.updateInventory();
    }

    public boolean isDiamondArmorPiece(ItemStack item) {
        return switch (item.getType()) {
            case DIAMOND_BOOTS, DIAMOND_LEGGINGS, DIAMOND_CHESTPLATE, DIAMOND_HELMET -> true;
            default -> false;
        };
    }

}
