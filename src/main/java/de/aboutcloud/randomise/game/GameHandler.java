package de.aboutcloud.randomise.game;

import de.aboutcloud.randomise.GameSettings;
import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.util.MySound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameHandler {

    private Randomise instance;

    public GameHandler(Randomise instance) {
        this.instance = instance;
    }

    public void startGame() {
        GameSettings.RUNNING = true;
        instance.getServer().getOnlinePlayers().forEach((player -> player.getInventory().clear()));

        if(GameSettings.RANDOMISE_CRAFTING) {
            instance.getRandomiserRecord().recipeRandomiser().buildIfNeeded();
        }

        if(GameSettings.RANDOMISE_DROP_BLOCK) {
            instance.getRandomiserRecord().blockDropRandomiser().buildIfNeeded();
        }

        if(GameSettings.RANDOMISE_DROP_ENTITTY) {
            instance.getRandomiserRecord().entityDropRandomiser().buildIfNeeded();
        }

        MySound.START.broadcast(instance);

        if(GameSettings.TIMER) {
            instance.getHandlerRecord().timerHandler().start();
        }
    }

    public void endGame() {
        GameSettings.RUNNING = false;
        instance.getHandlerRecord().timerHandler().stop();
        instance.getServer().getOnlinePlayers().forEach((player) -> {
            player.getInventory().clear();
            giveItems(player.getInventory());
        });
    }

    public void giveItems(Inventory inv) {
        inv.setItem(4, instance.getItemRecord().randomiserItem().getItem());
        inv.setItem(8, instance.getItemRecord().startItem().getItem());
        inv.setItem(0, instance.getItemRecord().timerItem().getItem());
    }


}
