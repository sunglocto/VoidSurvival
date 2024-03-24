package com.awokens.voidsurvival;

import com.awokens.voidsurvival.Commands.*;
import com.awokens.voidsurvival.Listeners.Interact.*;
import com.awokens.voidsurvival.Listeners.Mobs.*;
import com.awokens.voidsurvival.Listeners.Player.*;
import com.awokens.voidsurvival.Manager.LuckPermsUtils;
import com.awokens.voidsurvival.Manager.RecipeManager;
import com.awokens.voidsurvival.Manager.WorldResetManager;
import com.awokens.voidsurvival.Manager.VoidConfigManager;
import com.samjakob.spigui.SpiGUI;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public final class VoidSurvival extends JavaPlugin implements Listener {


    private static Plugin plugin;

    private static LuckPermsUtils luckPermsUtils;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static LuckPermsUtils getLuckPermsUtils() { return luckPermsUtils; }

    private static VoidConfigManager voidConfigManager;

    private static WorldResetManager mapResetScheduler;

    private static SpiGUI spiGUI;

    private static RecipeManager recipeManager;

    private static Team collisionTeam;

    public static RecipeManager getRecipeManager() { return recipeManager; }
    public static WorldResetManager getMapResetScheduler() {
        return mapResetScheduler;
    }

    public static VoidConfigManager configManager() {
        return voidConfigManager;
    }

    public static SpiGUI getSpiGUI() { return spiGUI; }

    public static Team getCollisionTeam() { return collisionTeam; }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).shouldHookPaperReload(true));

        new ToggleCmd();
        new NonchestCmd();
        new Respawn();
        new CommandsCmd();
        new RecipesCmd();
        new PromoLinksCmd();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        voidConfigManager = new VoidConfigManager(this, getDataFolder());

        luckPermsUtils = new LuckPermsUtils();

        mapResetScheduler = new WorldResetManager();

        spiGUI = new SpiGUI(this);

        recipeManager = new RecipeManager(this);

        CommandAPI.onEnable();
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
                new Join(),
                new Chat(),
                new Bat(),
                new SandMechanism(),
                new CustomSuspiciousSandDrops(),
                new LavaCauldronMechanism()
        ));

        new BukkitRunnable() {
            @Override
            public void run() {

                Material[] essentialItems = {
                        Material.DIRT,
                        Material.OAK_PLANKS,
                        Material.COBBLESTONE,
                        Material.IRON_NUGGET
                };

                // Creating an instance of Random class
                Random random = new Random();

                // Generating a random index within the bounds of the array
                int randomIndex = random.nextInt(essentialItems.length);

                // Getting the random element from the array
                Material randomElement = essentialItems[randomIndex];

                ItemStack item = new ItemStack(randomElement);

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (!luckPermsUtils.hasToggledItems(player)) continue;

                    player.getInventory().addItem(item);
                }

            }
        }.runTaskTimer(this, 0L, 20L * 10L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CommandAPI.onDisable();

        getMapResetScheduler().getMapResetBar().setVisible(false);
        getMapResetScheduler().getTask().cancel();
        Bukkit.clearRecipes();
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
