package com.awokens.voidsurvival.Listeners.Player;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChat implements Listener {

    @EventHandler
    public void chat(AsyncChatEvent event) {


        if (event.isCancelled()) return;

        event.setCancelled(true);

        String message =
                " <gray>" + event.getPlayer().getName()
                + ": <white>" + event.signedMessage().message();

        final Component component = MiniMessage.miniMessage().deserialize(message.trim());

        Bukkit.broadcast(component);
    }
}
