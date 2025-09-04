package de.aboutcloud.randomise.util;

import de.aboutcloud.randomise.Randomise;
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
                instance.getHandlerRecord().gameHandler().giveItems(p);
            }
        }
        try {
            instance.getDatabaseService().withTransaction(instance, c -> {
                try (var ps = c.prepareStatement("INSERT IGNORE INTO randomise(uuid, locale) VALUES (?, ?)")) {
                    ps.setString(1, p.getUniqueId().toString());
                    ps.setString(2, p.locale().toLanguageTag());
                    ps.executeUpdate();
                }
            });
        } catch (Exception exception) {
            instance.getLogger().warning("Insert failed: " + exception.getMessage());
        }
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
