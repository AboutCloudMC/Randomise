package de.aboutcloud.randomise;

import de.aboutcloud.cloudConfig.api.CloudConfigAPI;
import de.aboutcloud.cloudConfig.api.CloudConfigRegistration;
import de.aboutcloud.cloudConfig.api.CloudConfigService;
import de.aboutcloud.randomise.game.GameHandler;
import de.aboutcloud.randomise.game.HandlerRecord;
import de.aboutcloud.randomise.game.TimerHandler;
import de.aboutcloud.randomise.items.ItemRecord;
import de.aboutcloud.randomise.items.RandomiserItem;
import de.aboutcloud.randomise.items.StartItem;
import de.aboutcloud.randomise.items.TimerItem;
import de.aboutcloud.randomise.randomiser.RandomiseCommand;
import de.aboutcloud.randomise.randomiser.RandomiserRecord;
import de.aboutcloud.randomise.randomiser.drop.block.BlockDropRandomiser;
import de.aboutcloud.randomise.randomiser.drop.entity.EntityDropRandomiser;
import de.aboutcloud.randomise.randomiser.recipe.RecipeRandomiser;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Randomise extends JavaPlugin {

    private CloudConfigService service;

    private RandomiserRecord randomiserRecord;
    private ItemRecord itemRecord;
    private HandlerRecord handlerRecord;

    @Override
    public void onEnable() {
        // Plugin startup logic

        var ccs = CloudConfigAPI.get(this);
        ccs.register(this, new CloudConfigRegistration(
                List.of("config.yml"),
                "en-EN",
                "locale/en-EN.yml",
                true // copy defaults if missing
        ));
        this.service = ccs;

        var cfg = ccs.getConfig(this, "config.yml");


        getServer().getPluginManager().registerEvents(new EventHandle(this), this);

        itemRecord = new ItemRecord(
                new RandomiserItem(this),
                new StartItem(this),
                new TimerItem(this)
        );

        randomiserRecord = new RandomiserRecord(
                new RecipeRandomiser(this),
                new BlockDropRandomiser(this),
                new EntityDropRandomiser(this)
        );

        RandomiseCommand command = new RandomiseCommand(this);
        PluginCommand pc = getCommand("randomise");
        pc.setExecutor(command);
        pc.setTabCompleter(command);

        this.handlerRecord = new HandlerRecord(new GameHandler(this), new TimerHandler(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public RandomiserRecord getRandomiserRecord() {
        return randomiserRecord;
    }

    public ItemRecord getItemRecord() { return itemRecord; }

    public HandlerRecord getHandlerRecord() { return this.handlerRecord; }

    public CloudConfigService getService() { return this.service; }
}
