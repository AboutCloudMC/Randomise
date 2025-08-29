package de.aboutcloud.randomise.items;

import de.aboutcloud.randomise.Randomise;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractItem implements Listener {

    public final Randomise instance;
    //public final boolean isToggle;
    public boolean state;

    public AbstractItem(Randomise instance) {
        this.instance = instance;
        //this.isToggle = isToggle;
        this.state = false;
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    public abstract ItemStack getItem();

    public abstract Consumer<PlayerInteractEvent> onClick();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(Objects.equals(e.getItem(), getItem())) {
            e.setCancelled(true);
            state = !state;
            onClick().accept(e);
        }
    }


}
