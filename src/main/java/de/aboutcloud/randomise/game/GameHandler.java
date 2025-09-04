package de.aboutcloud.randomise.game;

import de.aboutcloud.randomise.util.GameSettings;
import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.util.LanguageUtil;
import de.aboutcloud.randomise.util.MySound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Locale;

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
            giveItems(player);
        });
    }

    public void giveItems(Player player) {
        Inventory inv = player.getInventory();
        Locale locale = LanguageUtil.getLocale(instance, player);
        inv.setItem(4, instance.getItemRecord().randomiserItem().getItem(locale));
        inv.setItem(8, instance.getItemRecord().startItem().getItem(locale));
        inv.setItem(0, instance.getItemRecord().timerItem().getItem(locale));
        inv.setItem(1, instance.getItemRecord().localeItem().getItem(locale));

    }


}
