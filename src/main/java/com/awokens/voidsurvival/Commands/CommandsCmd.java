package com.awokens.voidsurvival.Commands;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.ExecutableCommand;
import dev.jorel.commandapi.RegisteredCommand;
import jdk.jfr.Registered;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

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
