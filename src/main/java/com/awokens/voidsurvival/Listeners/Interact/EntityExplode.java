package com.awokens.voidsurvival.Listeners.Interact;

import com.awokens.voidsurvival.Manager.TNTTrail;
import com.awokens.voidsurvival.VoidSurvival;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {

    @EventHandler
    public void explode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed tnt)) return;

        event.setCancelled(true);

        for (Block connectedBlock : TNTTrail.getConnectedBlocks(tnt.getLocation().getBlock())) {
            if (TNTTrail.isRelative(connectedBlock)) {
                new TNTTrail(VoidSurvival.getPlugin(), connectedBlock, 60);
                break;
            }
        }
    }
}
