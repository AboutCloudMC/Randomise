// src/main/java/de/aboutcloud/randomise/entities/EntityDropListener.java
package de.aboutcloud.randomise.entities;

import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.randomiser.drop.entity.EntityDropRandomiser;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public final class EntityDropListener implements Listener {

    private final Randomise instance;
    private final EntityDropRandomiser randomiser;
    private final boolean applyInCreative;


    public EntityDropListener(EntityDropRandomiser randomiser, Randomise instance, boolean applyInCreative) {
        this.instance = instance;
        this.randomiser = randomiser;
        this.applyInCreative = applyInCreative;

        this.randomiser.buildIfNeeded();
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (!applyInCreative && killer != null && killer.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        ItemStack mapped = randomiser.mapDrop(event.getEntityType());
        if (mapped == null || mapped.getType().isAir()) return;

        event.getDrops().clear();
        event.getEntity().getWorld().dropItemNaturally(
                event.getEntity().getLocation(),
                mapped.clone()
        );

    }
}
