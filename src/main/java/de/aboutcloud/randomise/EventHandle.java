package de.aboutcloud.randomise;

import de.aboutcloud.randomise.gui.EasyButton;
import de.aboutcloud.randomise.gui.EasyGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

public class EventHandle implements Listener {

    private Randomise instance;

    public EventHandle(Randomise instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!GameSettings.RUNNING) {
            PlayerInventory inv = p.getInventory();
            inv.clear();
            if(p.hasPermission("randomise.admin")) {
                instance.getHandlerRecord().gameHandler().giveItems(inv);
            }
        }
        instance.getService().send(p, instance, "success", null);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player p)) return;
        if(event.getCurrentItem() == null) return;
        if (event.getClickedInventory().getHolder() == null) return;
        if (event.getClickedInventory().getHolder() instanceof EasyGUI gui) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            EasyButton btn = gui.getButtons().get(slot);
            if (btn != null) {btn.accept(event);}
        }
    }

}
