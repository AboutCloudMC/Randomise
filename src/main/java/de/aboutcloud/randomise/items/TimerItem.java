package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.GameSettings;
import de.aboutcloud.randomise.ItemBuilder;
import de.aboutcloud.randomise.Randomise;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.function.Consumer;

public class TimerItem extends AbstractItem {

    public TimerItem(Randomise instance) {
        super(instance);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.CLOCK)
                .setName(instance.getService().message(instance, "item.timer", Locale.ENGLISH, null))
                .build();
    }

    @Override
    public Consumer<PlayerInteractEvent> onClick() {
        return (e) -> {
            GameSettings.TIMER = !GameSettings.TIMER;
        };
    }
}
