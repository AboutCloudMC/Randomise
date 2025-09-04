package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.util.ItemBuilder;
import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.gui.EasyGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class LocaleItem extends AbstractItem {

    public LocaleItem(Randomise instance) {
        super(instance, Material.NAME_TAG, "item.locale");
    }

    @Override
    public Consumer<PlayerInteractEvent> onClick() {
        return (e) -> {
            Set<Locale> locales = instance.getConfigService().getAvailableLocales(instance);
            EasyGUI gui = new EasyGUI(Component.text("Select your language"), 4).setBackground();
            AtomicInteger currentSlot = new AtomicInteger(10);
            locales.forEach((locale) -> {
                gui.registerButton(currentSlot.get(), new ItemBuilder(Material.PAPER).setName(Component.text(locale.getDisplayName())).build(), (event -> {
                    try {
                        instance.getDatabaseService().withTransaction(instance, c -> {
                            try (var ps = c.prepareStatement("UPDATE randomise SET locale = ? WHERE uuid = ?")) {
                                ps.setString(1, locale.toLanguageTag());
                                ps.setString(2, event.getWhoClicked().getUniqueId().toString());
                                ps.executeUpdate();
                            }
                        });
                    } catch (Exception exception) {
                        instance.getLogger().warning("Selection failed: " + exception.getMessage());
                    }
                    instance.getHandlerRecord().gameHandler().giveItems(e.getPlayer());
                }), false);
                currentSlot.getAndIncrement();
            });
            gui.open(e.getPlayer());
        };
    }
}
