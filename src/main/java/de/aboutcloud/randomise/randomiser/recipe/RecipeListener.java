// src/main/java/de/aboutcloud/randomise/recipes/CraftingOverrideListener.java
package de.aboutcloud.randomise.randomiser.recipe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class RecipeListener implements Listener {
    private final RecipeRandomiser randomiser;

    public RecipeListener(RecipeRandomiser randomiser) {
        this.randomiser = randomiser;
    }

    @EventHandler
    public void onPrepare(PrepareItemCraftEvent e) {
        CraftingInventory inv = e.getInventory();
        Recipe recipe = e.getRecipe();
        if (recipe == null) return;

        ItemStack mapped = randomiser.mapResult(recipe);
        if (mapped != null) {
            inv.setResult(mapped);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        Recipe recipe = e.getRecipe();
        if (recipe == null) return;

        ItemStack mapped = randomiser.mapResult(recipe);
        if (mapped != null) {
            ItemStack take = mapped.clone();
            take.setAmount(e.getCurrentItem() != null ? e.getCurrentItem().getAmount() : mapped.getAmount());
            e.setCurrentItem(take);
        }
    }
}
