package com.awokens.voidsurvival.Commands;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.RegisteredCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;

public class CommandsCmd {


    public CommandsCmd() {

        new CommandAPICommand("commands")
                .withFullDescription("Provides a list of commands accessible to you")
                .executesPlayer((player, args) -> {

                    final Component title = MiniMessage.miniMessage().deserialize(
                            "<yellow>Commands:<newline>");

                    player.sendMessage(title);


                    HashMap<String, RegisteredCommand> commands = new HashMap<>();

                    for (RegisteredCommand command : CommandAPI.getRegisteredCommands()) {
                        if (!commands.containsKey(command.commandName())) {
                            commands.put(command.commandName(), command);
                        }
                    }

                    for (RegisteredCommand command : commands.values()) {

                        if (!player.hasPermission(command.permission().toString())) continue;;

                        String description = command.fullDescription()
                                .orElse("No description provided");

                        String name = command.commandName();

                        final Component component = MiniMessage.miniMessage().deserialize(
                                "→ <hover:show_text:'"+ description + "'>/" + name +"</hover>"
                        );

                        player.sendMessage(component);
                    }
                    
                }).register();
    }
}
