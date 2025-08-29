package de.aboutcloud.randomise.util;

import de.aboutcloud.randomise.GameSettings;
import de.aboutcloud.randomise.Randomise;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public enum MySound {

    SUCCESS(Sound.ENTITY_EXPERIENCE_ORB_PICKUP),
    FAILURE(Sound.BLOCK_NOTE_BLOCK_PLING),
    START(Sound.ITEM_GOAT_HORN_SOUND_0);

    private Sound sound;

    MySound(Sound sound) {
        this.sound = sound;
    }

    public void play(Player player) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public void play(Location location) {
        location.getWorld().playSound(location, sound, 1, 1);
    }


    public void broadcast(@NotNull World world) {
        world.getPlayers().forEach(this::play);
    }


    public void broadcast(Randomise instance) {
        instance.getServer().getOnlinePlayers().forEach(this::play);
    }

}
