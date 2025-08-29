package de.aboutcloud.randomise.gui;

import de.aboutcloud.randomise.ItemBuilder;
import de.aboutcloud.randomise.util.MySound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.function.Consumer;

public class EasyButton {

    private ItemStack item;
    private int slot;
    private EasyGUI gui;
    private boolean state;
    private boolean isToggle;
    private Consumer<InventoryClickEvent> callback;


    ItemStack INACTIVE = new ItemBuilder(Material.REDSTONE_BLOCK)
            .setName(Component.text("Inactive").color(NamedTextColor.RED))
            .build();
    ItemStack ACTIVE = new ItemBuilder(Material.EMERALD_BLOCK)
            .setName(Component.text("Active").color(NamedTextColor.GREEN))
            .build();

    public EasyButton(int slot, ItemStack item, boolean isToggle, EasyGUI gui, Consumer<InventoryClickEvent> callback) {
        this.item = item;
        this.isToggle = isToggle;
        this.slot = slot;
        this.gui = gui;
        this.callback = callback;
        this.state = false;
        if(isToggle) {
            gui.getInventory().setItem(slot+9, INACTIVE);
        }
    }

    public EasyButton accept(InventoryClickEvent event) {
        this.callback.accept(event);
        if(isToggle) {
            state = !state;
            gui.getInventory().setItem(slot+9, state ? ACTIVE : INACTIVE);
        }
        MySound.SUCCESS.play((Player) event.getWhoClicked());
        return this;
    }

    public Consumer<InventoryClickEvent> getCallback() {
        return callback;
    }

    public ItemStack getItem() {
        return item;
    }
}
