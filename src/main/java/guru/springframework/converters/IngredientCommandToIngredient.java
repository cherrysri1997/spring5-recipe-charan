package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.repositories.RecipeRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@SuppressWarnings("ConstantConditions")
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    private final RecipeRepository recipeRepository;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure, RecipeRepository recipeRepository) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Nullable
    @Synchronized
    public Ingredient convert(IngredientCommand source) {
        if(source == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setRecipe(recipeRepository.findById(source.getRecipeId()).orElse(null));
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(source.getUom()));
        return ingredient;
    }
}
