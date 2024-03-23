package com.awokens.voidsurvival.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class PromoLinksCmd {

    public PromoLinksCmd() {

        new CommandAPICommand("discord")
                .executesPlayer((player, args) -> {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<newline><click:open_url:'https://discord.gg/q3BRbWqHgx'>Click to join our Discord</click><newline>"
                    ));
                }).register();


    }
}
