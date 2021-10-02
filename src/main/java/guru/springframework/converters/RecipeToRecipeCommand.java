package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@SuppressWarnings("ConstantConditions")
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NotesToNotesCommand notesToNotesCommand;

    public RecipeToRecipeCommand(
            CategoryToCategoryCommand categoryToCategoryCommand,
            IngredientToIngredientCommand ingredientToIngredientCommand,
            NotesToNotesCommand notesToNotesCommand
    ) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
    }

    @Override
    @Nullable
    @Synchronized
    public RecipeCommand convert(Recipe source) {
        if(source == null) {
            return null;
        }
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));

        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.
                    getCategories().
                    forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.
                    getIngredients().
                    forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }
        return recipeCommand;
    }
}
