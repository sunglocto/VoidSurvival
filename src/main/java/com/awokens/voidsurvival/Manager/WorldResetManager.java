package com.awokens.voidsurvival.Manager;

import com.awokens.voidsurvival.VoidSurvival;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.swing.border.Border;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WorldResetManager {
    private final BukkitTask task;

    private final BossBar mapResetBar;

    private final VoidSurvival plugin;
    public WorldResetManager(VoidSurvival plugin) {

        this.plugin = plugin;

        mapResetBar = Bukkit.createBossBar(
                "Loading...",
                BarColor.GREEN,
                BarStyle.SEGMENTED_20,
                BarFlag.PLAY_BOSS_MUSIC);

        mapResetBar.setVisible(true);

        mapResetBar.setProgress(1.0);
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (plugin.luckPermsUtils().hasBossBarToggled(player)) {
                mapResetBar.addPlayer(player);
            }
        }
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                long timeLeft = updateTime();
                mapResetBar.setProgress(percentage(timeLeft));
                mapResetBar.setTitle("Overworld will clear in: " + formatUnix(timeLeft));
            }
        }.runTaskTimer(plugin, 20L, 20L);

    }

    public BossBar getMapResetBar() {
        return this.mapResetBar;
    }

    public BukkitTask getTask() {
        return this.task;
    }

    public long updateTime() {
        long timestamp = plugin.configManager()
                .getWorldResetTimer();

        timestamp -= 1;

        if (timestamp < 1) {
            timestamp = 60 * 60 * 24 * 2; // Set default timer if not set
            plugin.configManager()
                    .getVoidConfig()
                    .set("world_reset_timer", timestamp);
        } else {
            plugin.configManager()
                    .getVoidConfig()
                    .set("world_reset_timer", timestamp);
        }

        plugin.configManager().saveConfig();

        return timestamp;
    }

    public double percentage(long currentValue) {

        double value = ((double) currentValue / (60L * 60L * 24L * 2L));

        if (value > 1.0D) value = 1.0D;

        if (value < 0.0D) value = 0.0D;

        return value;
    }


    public String formatUnix(long timestamp) {
        // Convert Unix timestamp to milliseconds
        long milliseconds = timestamp * 1000;

        // Convert milliseconds to days, hours, minutes, and seconds
        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;

        // Construct the formatted string
        StringBuilder formattedTime = new StringBuilder();
        if (days > 0) {
            formattedTime.append(days).append("d ");
        }
        if (hours > 0) {
            formattedTime.append(hours).append("h ");
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append("m ");
        }
        if (seconds > 0 || (days == 0 && hours == 0 && minutes == 0)) {
            formattedTime.append(seconds).append("s");
        }

        return formattedTime.toString().trim();
    }
}
