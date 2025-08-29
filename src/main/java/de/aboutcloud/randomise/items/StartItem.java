package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.ItemBuilder;
import de.aboutcloud.randomise.Randomise;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.function.Consumer;

public class StartItem extends AbstractItem {

    public StartItem(Randomise instance) {
        super(instance);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.EMERALD)
                .setName(instance.getService().message(instance, "item.start", Locale.ENGLISH, null))
                .build();
    }

    @Override
    public Consumer<PlayerInteractEvent> onClick() {
        return (e) -> {
            instance.getHandlerRecord().gameHandler().startGame();
        };
    }
}
