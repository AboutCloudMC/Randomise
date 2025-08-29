package de.aboutcloud.randomise.randomiser.drop.entity;

import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.entities.EntityDropListener;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.security.SecureRandom;
import java.util.*;

public final class EntityDropRandomiser {

    private final Map<EntityType, ItemStack> mapping = new HashMap<>();
    private boolean built = false;

    public EntityDropRandomiser(Randomise instance) {
        instance.getServer().getPluginManager().registerEvents(new EntityDropListener(this, instance, true), instance);
    }

    public Map<EntityType, ItemStack> mapping() {
        return Collections.unmodifiableMap(mapping);
    }

    public void buildIfNeeded() {
        if (built) return;

        List<EntityType> sources = new ArrayList<>();
        for (EntityType t : EntityType.values()) {
            if (!t.isAlive()) continue;
            if (!t.isSpawnable()) continue;
            if (t == EntityType.PLAYER) continue;
            sources.add(t);
        }

        List<ItemStack> results = new ArrayList<>();
        Set<Material> blacklist = Set.of(
                Material.AIR, Material.BEDROCK, Material.BARRIER, Material.LIGHT,
                Material.COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK,
                Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.JIGSAW, Material.DEBUG_STICK
        );
        for (Material m : Material.values()) {
            if (!m.isItem()) continue;
            if (blacklist.contains(m)) continue;
            results.add(new ItemStack(m, 1));
        }

        if (sources.isEmpty() || results.isEmpty()) {
            built = true;
            return;
        }

        Random rng = new SecureRandom();
        Collections.shuffle(results, rng);

        int rSize = results.size();
        for (int i = 0; i < sources.size(); i++) {
            EntityType src = sources.get(i);
            ItemStack out = results.get(i % rSize).clone();
            mapping.put(src, out);
        }

        built = true;
    }

    public void rebuild() {
        mapping.clear();
        built = false;
        buildIfNeeded();
    }

    public ItemStack mapDrop(EntityType type) {
        ItemStack out = mapping.get(type);
        return out == null ? null : out.clone();
    }
}
