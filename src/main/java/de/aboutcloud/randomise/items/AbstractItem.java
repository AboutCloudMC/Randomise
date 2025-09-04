package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.util.ItemBuilder;
import de.aboutcloud.randomise.util.LanguageUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractItem implements Listener {

    public final Randomise instance;
    //public final boolean isToggle;
    public boolean state;
    public final Material material;
    public final String location;

    public AbstractItem(Randomise instance, Material material, String location) {
        this.instance = instance;
        //this.isToggle = isToggle;
        this.state = false;
        this.material = material;
        this.location = location;
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    public ItemStack getItem(Locale locale) {
        return new ItemBuilder(material)
                .setName(instance.getConfigService().message(instance, location, locale, null))
                .build();
    }

    public abstract Consumer<PlayerInteractEvent> onClick();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(Objects.equals(e.getItem(), getItem(LanguageUtil.getLocale(instance, e.getPlayer())))) {
            e.setCancelled(true);
            state = !state;
            onClick().accept(e);
        }
    }


}
