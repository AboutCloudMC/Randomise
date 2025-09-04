package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.util.GameSettings;
import de.aboutcloud.randomise.util.ItemBuilder;
import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.gui.EasyGUI;
import de.aboutcloud.randomise.util.LanguageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.function.Consumer;

public class RandomiserItem extends AbstractItem {

    public RandomiserItem(Randomise instance) {
        super(instance, Material.CHEST, "item.randomiser");
    }

    @Override
    public Consumer<PlayerInteractEvent> onClick() {
        return (e) -> {

            Locale locale = LanguageUtil.getLocale(instance, e.getPlayer());

            ItemStack crafting = new ItemBuilder(Material.CRAFTING_TABLE)
                    .setName(instance.getConfigService().message(instance, "randomiser.crafting", locale, null))
                    .build();
            ItemStack drop_block = new ItemBuilder(Material.GRASS_BLOCK)
                    .setName(instance.getConfigService().message(instance, "randomiser.drop_block", locale, null))
                    .build();
            ItemStack drop_entity = new ItemBuilder(Material.ZOMBIE_SPAWN_EGG)
                    .setName(instance.getConfigService().message(instance, "randomiser.drop_entity", locale, null))
                    .build();


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
