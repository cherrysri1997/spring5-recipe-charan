package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand getIngredientCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

}
