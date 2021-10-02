package guru.springframework.services.serviceImplementations;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@SuppressWarnings("ConstantConditions")
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;
    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeService recipeService, IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeService = recipeService;
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand getIngredientCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        return ingredientToIngredientCommand.convert(
                ingredientRepository.findIngredientByIdAndRecipe(
                        ingredientId,
                        recipeService.getRecipeById(recipeId)
                )
        );
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        if(ingredientCommand == null)
            return null;
        return ingredientToIngredientCommand.convert(ingredientRepository.save(ingredientCommandToIngredient.convert(ingredientCommand)));
    }

    @Override
    @Transactional
    public void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        ingredientRepository.deleteByIdAndRecipe(ingredientId, recipeService.getRecipeById(recipeId));
    }
}
