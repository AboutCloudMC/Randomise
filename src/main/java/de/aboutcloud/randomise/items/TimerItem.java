package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.util.GameSettings;
import de.aboutcloud.randomise.util.ItemBuilder;
import de.aboutcloud.randomise.Randomise;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.function.Consumer;

public class TimerItem extends AbstractItem {

    public TimerItem(Randomise instance) {
        super(instance, Material.CLOCK, "item.timer");
    }

    @Override
    public Consumer<PlayerInteractEvent> onClick() {
        return (e) -> {
            GameSettings.TIMER = !GameSettings.TIMER;
        };
    }
}
