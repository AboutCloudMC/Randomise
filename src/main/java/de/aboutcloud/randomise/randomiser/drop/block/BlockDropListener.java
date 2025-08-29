package de.aboutcloud.randomise.randomiser.drop.block;

import de.aboutcloud.randomise.GameSettings;
import de.aboutcloud.randomise.Randomise;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

public final class BlockDropListener implements Listener {

    private final Randomise instance;
    private final BlockDropRandomiser randomiser;
    private final boolean applyInCreative;

    public BlockDropListener(BlockDropRandomiser randomiser, Randomise instance, boolean applyInCreative) {
        this.instance = instance;
        this.randomiser = randomiser;
        this.applyInCreative = applyInCreative;

        this.randomiser.buildIfNeeded();

        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDrop(BlockDropItemEvent event) {
        if (!applyInCreative && event.getPlayer() != null &&
                event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if(!GameSettings.RANDOMISE_DROP_BLOCK) return;

        Material src = event.getBlockState().getType();
        ItemStack mapped = randomiser.mapDrop(src);
        if (mapped == null || mapped.getType().isAir()) return;

        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        event.getBlock().getWorld().dropItemNaturally(
                event.getBlock().getLocation(),
                mapped.clone()
        );
    }

    @EventHandler(ignoreCancelled = true)
    public void onExplode(EntityExplodeEvent event) {
        event.blockList().forEach(block -> {
            ItemStack mapped = randomiser.mapDrop(block.getType());
            if (mapped == null || mapped.getType().isAir()) return;

            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), mapped.clone());
        });
    }
}
