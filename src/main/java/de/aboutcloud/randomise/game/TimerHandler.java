package de.aboutcloud.randomise.game;

import de.aboutcloud.randomise.Randomise;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Locale;
import java.util.Map;

public class TimerHandler {

    private final Randomise plugin;
    private BukkitTask task;
    private int seconds = 0;

    public TimerHandler(Randomise plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (task != null) return;

        sendToAll();

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            seconds++;
            sendToAll();
        }, 20L, 20L);
    }


    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void reset() {
        seconds = 0;
        sendToAll();
    }

    public boolean isRunning() { return task != null; }
    public int getSeconds() { return seconds; }


    private void sendToAll() {
        Component msg = plugin.getConfigService().message(plugin, "timer_actionbar", Locale.ENGLISH, Map.of("timer", format(seconds)));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendActionBar(msg);
        }
    }

    private static String format(int totalSeconds) {
        int h = totalSeconds / 3600;
        int m = totalSeconds / 60;
        int s = totalSeconds % 60;
        if (h > 0) {
            return String.format("%02d:%02d:%02d", h, m, s);
        } else {
            return String.format("%02d:%02d", m, s);
        }
    }



}
