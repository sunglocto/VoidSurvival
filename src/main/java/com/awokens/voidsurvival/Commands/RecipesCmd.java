package com.awokens.voidsurvival.Commands;

import com.awokens.voidsurvival.VoidSurvival;
import dev.jorel.commandapi.CommandAPICommand;

public class RecipesCmd {

    public RecipesCmd() {
        new CommandAPICommand("recipes")
                .withFullDescription("View custom recipes")
                .executesPlayer((player, args) -> {
                    player.openInventory(VoidSurvival.getRecipeManager().getRecipeMenu());
                }).register();
    }
}
