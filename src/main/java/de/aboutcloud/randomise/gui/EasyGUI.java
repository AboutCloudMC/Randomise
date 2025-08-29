package de.aboutcloud.randomise.gui;

import de.aboutcloud.randomise.EventHandle;
import de.aboutcloud.randomise.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EasyGUI implements InventoryHolder {

    private Inventory inventory;
    private Map<Integer, EasyButton> buttons;

    public static ItemStack PANE = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
            .setName(Component.text(" "))
            .build();

    public EasyGUI(Component title, int rows) {
        inventory = Bukkit.createInventory(this, rows*9, title);
        buttons = new HashMap<>();
    }

    public EasyGUI registerButton(int slot, ItemStack item, Consumer<InventoryClickEvent> callback, boolean isToggle) {
        inventory.setItem(slot, item);
        EasyButton btn = new EasyButton(slot, item, isToggle, this, callback);
        buttons.put(slot, btn);
        return this;
    }

    public EasyGUI setBackground(ItemStack background) {
        for(int slot = 0; slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, background);
        }
        return this;
    }

    public EasyGUI setBackground() {
        setBackground(PANE);
        return this;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Integer, EasyButton> getButtons() {
        return buttons;
    }
}
