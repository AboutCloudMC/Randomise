package de.aboutcloud.randomise.randomiser;

import de.aboutcloud.cloudConfig.api.CloudConfigService;
import de.aboutcloud.randomise.Randomise;
import de.aboutcloud.randomise.randomiser.drop.block.BlockDropRandomiser;
import de.aboutcloud.randomise.randomiser.drop.entity.EntityDropRandomiser;
import de.aboutcloud.randomise.randomiser.recipe.RecipeRandomiser;

import de.aboutcloud.randomise.util.MySound;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class RandomiseCommand implements CommandExecutor, TabCompleter {

    private final Randomise instance;
    private final BlockDropRandomiser blockRand;
    private final EntityDropRandomiser entityRand;
    private final RecipeRandomiser recipeRand;

    private final CloudConfigService configService;

    public RandomiseCommand(Randomise instance) {
        this.instance = instance;
        this.blockRand = instance.getRandomiserRecord().blockDropRandomiser();
        this.entityRand = instance.getRandomiserRecord().entityDropRandomiser();
        this.recipeRand = instance.getRandomiserRecord().recipeRandomiser();
        this.configService = instance.getService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("randomise.admin")) {
            configService.send(sender, instance, "no_permission", null);
            if(sender instanceof Player player) MySound.FAILURE.play(player);
            return true;
        }

        if (args.length != 1) {
            configService.send(sender, instance, "usage", Map.of("command", label));
            if(sender instanceof Player player) MySound.FAILURE.play(player);
            return true;
        }

        String which = args[0].toLowerCase();
        switch (which) {
            case "drop_blocks" -> {
                blockRand.rebuild();
                configService.send(sender, instance, "rebuild.drop_block", null);
            }
            case "drop_entities" -> {
                entityRand.rebuild();
                configService.send(sender, instance, "rebuild.drop_entity", null);
            }
            case "crafting" -> {
                recipeRand.rebuild();
                configService.send(sender, instance, "rebuild.crafting", null);
            }
            default -> {
                configService.send(sender, instance, "rebuild.unknown", Map.of("option", which));
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> opts = List.of("drop_blocks", "drop_entities", "crafting");
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            List<String> out = new ArrayList<>();
            for (String o : opts) if (o.startsWith(prefix)) out.add(o);
            return out;
        }
        return List.of();
    }
}
