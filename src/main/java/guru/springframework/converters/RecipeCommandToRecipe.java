package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@SuppressWarnings("ConstantConditions")
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecipeCommandToRecipe(
            CategoryCommandToCategory categoryCommandToCategory,
            IngredientCommandToIngredient ingredientCommandToIngredient,
            NotesCommandToNotes notesCommandToNotes
    ) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Override
    @Nullable
    @Synchronized
    public Recipe convert(RecipeCommand source) {
        if(source == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(recipe.getServings());
        recipe.setUrl(recipe.getUrl());
        recipe.setSource(source.getSource());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));

        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.
                    getCategories().
                    forEach(categoryCommand -> recipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.
                    getIngredients().
                    forEach(ingredientCommand -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand)));
        }
        return recipe;
    }
}
