package de.aboutcloud.randomise;

import de.aboutcloud.cloudConfig.api.config.CloudConfigAPI;
import de.aboutcloud.cloudConfig.api.config.CloudConfigRegistration;
import de.aboutcloud.cloudConfig.api.config.CloudConfigService;
import de.aboutcloud.cloudConfig.api.databasse.CloudDatabaseAPI;
import de.aboutcloud.cloudConfig.api.databasse.CloudDatabaseService;
import de.aboutcloud.randomise.game.GameHandler;
import de.aboutcloud.randomise.game.HandlerRecord;
import de.aboutcloud.randomise.game.TimerHandler;
import de.aboutcloud.randomise.items.*;
import de.aboutcloud.randomise.randomiser.RandomiseCommand;
import de.aboutcloud.randomise.randomiser.RandomiserRecord;
import de.aboutcloud.randomise.randomiser.drop.block.BlockDropRandomiser;
import de.aboutcloud.randomise.randomiser.drop.entity.EntityDropRandomiser;
import de.aboutcloud.randomise.randomiser.recipe.RecipeRandomiser;
import de.aboutcloud.randomise.util.EventHandle;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Randomise extends JavaPlugin {

    private CloudConfigService ccs;
    private CloudDatabaseService cdbs;

    private RandomiserRecord randomiserRecord;
    private ItemRecord itemRecord;
    private HandlerRecord handlerRecord;

    @Override
    public void onEnable() {
        // Plugin startup logic

        var ccs = CloudConfigAPI.get(this);
        ccs.register(this, new CloudConfigRegistration(
                List.of("config.yml", "database.yml"),
                "en-EN",
                "locale/en-EN.yml",
                true // copy defaults if missing
        ));
        this.ccs = ccs;

        var cdbs = CloudDatabaseAPI.get(this);
        try {
            cdbs.ensurePool(this);   // reads plugins/AntiCheat/config/database.yml
            cdbs.migrate(this);      // optional: if not using runOnStartup or triggering manually
        } catch (Exception e) {
            getLogger().severe("DB init failed: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.cdbs = cdbs;

        var cfg = ccs.getConfig(this, "config.yml");


        getServer().getPluginManager().registerEvents(new EventHandle(this), this);

        itemRecord = new ItemRecord(
                new RandomiserItem(this),
                new StartItem(this),
                new TimerItem(this),
                new LocaleItem(this)
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

    public CloudConfigService getConfigService() { return this.ccs; }

    public CloudDatabaseService getDatabaseService() { return this.cdbs; }
}
