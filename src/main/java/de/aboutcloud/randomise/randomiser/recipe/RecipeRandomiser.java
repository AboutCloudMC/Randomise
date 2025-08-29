// src/main/java/de/aboutcloud/randomise/recipes/RecipeRandomiser.java
package de.aboutcloud.randomise.randomiser.recipe;

import de.aboutcloud.randomise.Randomise;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Keyed;

import java.security.SecureRandom;
import java.util.*;

public final class RecipeRandomiser {
    private final Map<NamespacedKey, ItemStack> mapping = new HashMap<>();
    private boolean built = false;

    public RecipeRandomiser(Randomise instance) {
        instance.getServer().getPluginManager().registerEvents(new RecipeListener(this), instance);
    }

    public Map<NamespacedKey, ItemStack> mapping() { return mapping; }

    public void buildIfNeeded() {
        if (built) return;

        List<Keyed> keyedRecipes = new ArrayList<>();
        List<ItemStack> results = new ArrayList<>();

        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe r = it.next();

            if (!(r instanceof CraftingRecipe cr)) continue;
            if (!(r instanceof Keyed k)) continue;

            ItemStack result = cr.getResult();
            if (result == null || result.getType().isAir()) continue;

            keyedRecipes.add(k);
            results.add(result.clone());
        }

        Random random = new SecureRandom();
        Collections.shuffle(results, random);

        int n = Math.min(keyedRecipes.size(), results.size());
        for (int i = 0; i < n; i++) {
            NamespacedKey key = keyedRecipes.get(i).getKey();
            ItemStack out = results.get(i).clone();
            mapping.put(key, out);
        }

        built = true;
    }

    public void rebuild() {
        mapping.clear();
        built = false;
        buildIfNeeded();
    }

    public ItemStack mapResult(Recipe recipe) {
        if (!(recipe instanceof Keyed k)) return null;
        ItemStack out = mapping.get(k.getKey());
        return out == null ? null : out.clone();
    }
}
