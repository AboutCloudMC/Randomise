package de.aboutcloud.randomise.randomiser.drop.block;

import de.aboutcloud.randomise.Randomise;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.security.SecureRandom;
import java.util.*;

public class BlockDropRandomiser {

    private final Map<Material, ItemStack> mapping = new HashMap<>();
    private boolean built = false;

    public BlockDropRandomiser(Randomise instance) {
        instance.getServer().getPluginManager().registerEvents(new BlockDropListener(this, instance, true), instance);
    }

    public Map<Material, ItemStack> mapping() {
        return Collections.unmodifiableMap(mapping);
    }

    public void buildIfNeeded() {
        if (built) return;

        List<Material> sourceBlocks = new ArrayList<>();
        for (Material m : Material.values()) {
            if (!m.isBlock()) continue;
            if (m == Material.AIR || m == Material.CAVE_AIR || m == Material.VOID_AIR) continue;
            sourceBlocks.add(m);
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

        if (sourceBlocks.isEmpty() || results.isEmpty()) {
            built = true;
            return;
        }

        Random rnd = new SecureRandom();
        Collections.shuffle(results, rnd);

        int rSize = results.size();
        for (int i = 0; i < sourceBlocks.size(); i++) {
            Material src = sourceBlocks.get(i);
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

    /**
     * Lookup helper. Returns a CLONE so callers don’t mutate our saved mapping.
     * @param sourceBlock the block type that was broken
     * @return the mapped drop, or null if none
     */
    public ItemStack mapDrop(Material sourceBlock) {
        ItemStack out = mapping.get(sourceBlock);
        return out == null ? null : out.clone();
    }

}
