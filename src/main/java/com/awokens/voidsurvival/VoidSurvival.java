package com.awokens.voidsurvival;

import com.awokens.voidsurvival.Listeners.Interact.*;
import com.awokens.voidsurvival.Listeners.Mobs.*;
import com.awokens.voidsurvival.Listeners.Player.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;

public final class VoidSurvival extends JavaPlugin implements Listener {


    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        registerListeners(this, List.of(
                new Drowned(),
                new Fishing(),
                new Death(),
                new PortalEnter(),
                new WorldGuard(),
                new EntityExplode(),
                new Blaze(),
                new Guardian(),
                new WanderingTrader(),
                new Witch(),
                new CraftingTable(),
                new DirtConvertToMoss(),
                new EntityExplode(),
                new HandTradeSwap(),
                new NautilusTreasure(),
                new GodVillager(),
                new Quit(),
                new Join()
        ));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners(Plugin plugin, List<Listener> listeners) {
        for (Listener listener : listeners) {
            try {
                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            } catch (NullPointerException e) {
                getLogger().log(Level.WARNING, "Failed to load listeners");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }
    }
}
