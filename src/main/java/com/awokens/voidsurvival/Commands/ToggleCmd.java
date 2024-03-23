package com.awokens.voidsurvival.Commands;

import com.awokens.voidsurvival.VoidSurvival;
import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ToggleCmd {

    public ToggleCmd() {

        CommandAPICommand items = new CommandAPICommand("items")
                .executesPlayer((player, args) -> {

                    boolean toggled = VoidSurvival.getLuckPermsUtils().hasToggledItems(player);
                    toggled = !toggled;

                    VoidSurvival.getLuckPermsUtils().setToggledItems(player, toggled);

                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "Toggled items to <gray>" + toggled
                    ));
                });

        CommandAPICommand bossbar = new CommandAPICommand("bossbar")
                .withFullDescription("Disable the World Map reset bossbar")
                .executesPlayer((player, args) -> {

                    boolean toggled = VoidSurvival.getLuckPermsUtils().hasBossBarToggled(player);

                    toggled = !toggled;

                    if (!toggled) {
                        VoidSurvival.getMapResetScheduler().getMapResetBar().removePlayer(player);
                    } else {
                        VoidSurvival.getMapResetScheduler().getMapResetBar().addPlayer(player);
                    }

                    VoidSurvival.getLuckPermsUtils().setBossBarToggled(player, toggled);

                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "Toggled bossbar to <gray>" + toggled
                    ));
                });

        new CommandAPICommand("toggle")
                .withFullDescription("Toggle on or off certain features")
                .withUsage("/toggle <items, bossbar, stats>")
                .withSubcommands(items, bossbar)
                .register();

    }
}
