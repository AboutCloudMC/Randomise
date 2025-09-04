package de.aboutcloud.randomise.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    private static final MiniMessage MINI = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    /**
     * Creates a new ItemBuilder for the given material.
     */
    public ItemBuilder(@NotNull Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    /**
     * Creates a new ItemBuilder with specified material and amount.
     */
    public ItemBuilder(@NotNull Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    /**
     * Wraps an existing ItemStack in the builder.
     */
    public ItemBuilder(@NotNull ItemStack itemStack) {
        this.item = itemStack.clone();
        this.meta = item.getItemMeta();
    }

    /**
     * Sets the display name using an Adventure Component.
     */
    public ItemBuilder setName(@NotNull Component name) {
        meta.displayName(name);
        return this;
    }

    /**
     * Sets the display name with MiniMessage format.
     */
    public ItemBuilder setName(@NotNull String miniMessage) {
        Component comp = MINI.deserialize(miniMessage);
        meta.displayName(comp);
        return this;
    }

    /**
     * Sets the display name from a legacy string with '&' color codes.
     */
    public ItemBuilder setNameLegacy(@NotNull String legacyString) {
        Component comp = LEGACY.deserialize(legacyString);
        meta.displayName(comp);
        return this;
    }

    /**
     * Sets the lore lines (overwrites existing lore) using MiniMessage.
     */
    public ItemBuilder setLore(@NotNull List<String> miniLore) {
        List<Component> loreComp = new ArrayList<>();
        for (String line : miniLore) {
            loreComp.add(MINI.deserialize(line));
        }
        meta.lore(loreComp);
        return this;
    }

    /**
     * Adds lore lines to existing lore using MiniMessage.
     */
    public ItemBuilder addLore(@NotNull String... lines) {
        List<Component> loreComp = meta.hasLore() ? new ArrayList<>(meta.lore()) : new ArrayList<>();
        for (String line : lines) {
            loreComp.add(MINI.deserialize(line));
        }
        meta.lore(loreComp);
        return this;
    }

    /**
     * Adds or updates an enchantment.
     */
    public ItemBuilder addEnchant(@NotNull Enchantment enchantment, int level, boolean ignoreRestrictions) {
        meta.addEnchant(enchantment, level, ignoreRestrictions);
        return this;
    }

    /**
     * Removes an enchantment.
     */
    public ItemBuilder removeEnchant(@NotNull Enchantment enchantment) {
        meta.removeEnchant(enchantment);
        return this;
    }

    /**
     * Sets whether the item is unbreakable.
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Sets custom model data (resource pack model override).
     */
    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    /**
     * Adds item flags (e.g., hide attributes, hide enchants).
     */
    public ItemBuilder addItemFlags(@NotNull ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Removes item flags.
     */
    public ItemBuilder removeItemFlags(@NotNull ItemFlag... flags) {
        meta.removeItemFlags(flags);
        return this;
    }

    /**
     * Finalizes the ItemStack and returns it.
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Shortcut to quickly create a named item with MiniMessage name.
     */
    public static ItemStack createNamed(@NotNull Material material, @NotNull String miniMessage) {
        return new ItemBuilder(material)
                .setName(miniMessage)
                .build();
    }
}
