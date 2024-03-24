package com.awokens.voidsurvival.Listeners.Player;

import com.awokens.voidsurvival.VoidSurvival;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

public class Chat implements Listener {

    private final String pattern = "\\[(item|inv)]";
    private final String itemPattern = "\\[item\\]";
    private final String invPattern = "\\[inv\\]";

    @EventHandler
    public void chat(AsyncChatEvent event) {

        if (event.isCancelled()) return;

        event.setCancelled(true);

        String message = event.signedMessage().message().trim();


        if (message.matches(itemPattern)) {
            item(event.getPlayer());
            return;
        }
        if (message.matches(invPattern)) {
            inv(event.getPlayer());
            return;
        }


        message = "<gray>" + event.getPlayer().getName()
                + ": <white>" + message;

        final Component component = MiniMessage.miniMessage().deserialize(message.trim());

        Bukkit.broadcast(component);
    }

    public void item(Player player) {

        ItemStack item = player.getInventory().getItemInMainHand();

        int amount = item.getAmount();
        String name = item.getType().name().replaceAll("_", " ").toLowerCase();
        HoverEvent<HoverEvent.ShowItem> hover = Bukkit.getItemFactory().asHoverEvent(item,
                showItem -> showItem);

        final Component component = MiniMessage.miniMessage().deserialize(
                "<gray>"+ player.getName() + ": <gray>[<aqua>" + amount +
                        "x of " + name +
                        "</aqua>]</gray>"
        ).hoverEvent(hover);

        Bukkit.broadcast(component);

    }

    public void inv(Player player) {

        ItemStack[] items = player.getInventory().getContents();

        ItemStack bundle = new ItemStack(Material.BUNDLE);

        BundleMeta meta = (BundleMeta) bundle.getItemMeta();


        SGMenu snapshot = VoidSurvival.getSpiGUI().create("&8Inventory Snapshot", 5);
        for (ItemStack item : items) {
            if (item == null || item.isEmpty()) continue;
            meta.addItem(item);
            snapshot.addButton(new SGButton(item));
        }

        meta.displayName(MiniMessage.miniMessage().deserialize(
                "<yellow>" + player.getName() + "'s Inventory Snapshot"
        ).decoration(TextDecoration.ITALIC, false));

        bundle.setItemMeta(meta);

        for (Player individual : Bukkit.getOnlinePlayers()) {
            HoverEvent<HoverEvent.ShowItem> hover = Bukkit.getItemFactory().asHoverEvent(bundle,
                    showItem -> showItem);

            final Component component = MiniMessage.miniMessage().deserialize(
                            "<gray>"+ player.getName() + ": " + "<gray>[" + "<yellow>Inventory Snapshot<gray>]")
                    .hoverEvent(hover)
                    .clickEvent(ClickEvent.callback((callback) -> {
                        individual.openInventory(snapshot.getInventory());
                    }));
            individual.sendMessage(component);
        }
    }
}
