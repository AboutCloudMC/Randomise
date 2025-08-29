package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.GameSettings;
import de.aboutcloud.randomise.ItemBuilder;
import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.gui.EasyGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.function.Consumer;

public class RandomiserItem extends AbstractItem {


    public RandomiserItem(Randomise instance) {
        super(instance);
    }

    private final ItemStack crafting = new ItemBuilder(Material.CRAFTING_TABLE)
            .setName(instance.getService().message(instance, "randomiser.crafting", Locale.ENGLISH, null))
            .build();
    private final ItemStack drop_block = new ItemBuilder(Material.GRASS_BLOCK)
            .setName(instance.getService().message(instance, "randomiser.drop_block", Locale.ENGLISH, null))
            .build();
    private final ItemStack drop_entity = new ItemBuilder(Material.ZOMBIE_SPAWN_EGG)
            .setName(instance.getService().message(instance, "randomiser.drop_entity", Locale.ENGLISH, null))
            .build();

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.CHEST)
                .setName(instance.getService().message(instance, "item.randomiser", Locale.ENGLISH, null))
                .build();
    }

    @Override
    public Consumer<PlayerInteractEvent> onClick() {
        return (e) -> {
            EasyGUI gui = new EasyGUI(Component.text("Test"), 4)
                    .setBackground()
                    .registerButton(12,crafting, (event) -> {
                        GameSettings.RANDOMISE_CRAFTING = true;
                    }, true)
                    .registerButton(13, drop_block, (event) -> {
                        GameSettings.RANDOMISE_DROP_BLOCK = true;
                    }, true)
                    .registerButton(14, drop_entity, (event) -> {
                        GameSettings.RANDOMISE_DROP_ENTITTY = true;
                    }, true);
            gui.open(e.getPlayer());
        };
    }
}
