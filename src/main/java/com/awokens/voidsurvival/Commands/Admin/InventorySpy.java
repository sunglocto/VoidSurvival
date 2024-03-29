package com.awokens.voidsurvival.Commands.Admin;

import com.awokens.voidsurvival.VoidSurvival;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class InventorySpy {
    public InventorySpy(VoidSurvival plugin) {

        Collection<String> playerNames = new ArrayList<>();

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            playerNames.add(player.getName());
        }

        CommandAPICommand enderchest = new CommandAPICommand("enderchest")
                .withArguments(new PlayerArgument("target").replaceSuggestions(
                        ArgumentSuggestions.strings(playerNames)))
                .executesPlayer((player, args) -> {
                    Player target = (Player) args.get(0);
                    if (target == null) return;
                    player.openInventory(target.getEnderChest());
                });

        CommandAPICommand inventory = new CommandAPICommand("inventory")
                .withArguments(new PlayerArgument("target").replaceSuggestions(
                        ArgumentSuggestions.strings(playerNames)))
                .executesPlayer((player, args) -> {
                    Player target = (Player) args.get(0);
                    if (target == null) return;
                    player.openInventory(target.getInventory());
                });

        new CommandAPICommand("inventoryspy")
                .withAliases("invspy", "invispy", "spy")
                .withPermission(CommandPermission.OP)
                .withSubcommands(enderchest, inventory)
                .register();
    }


}
