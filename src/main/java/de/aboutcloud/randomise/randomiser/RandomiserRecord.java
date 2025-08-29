package de.aboutcloud.randomise.randomiser;

import de.aboutcloud.randomise.randomiser.drop.block.BlockDropRandomiser;
import de.aboutcloud.randomise.randomiser.drop.entity.EntityDropRandomiser;
import de.aboutcloud.randomise.randomiser.recipe.RecipeRandomiser;

public record RandomiserRecord(RecipeRandomiser recipeRandomiser,
                               BlockDropRandomiser blockDropRandomiser,
                               EntityDropRandomiser entityDropRandomiser) { }
