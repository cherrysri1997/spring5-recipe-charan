package guru.springframework.services.serviceImplementations;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(
            RecipeRepository recipeRepository,
            RecipeCommandToRecipe recipeCommandToRecipe,
            RecipeToRecipeCommand recipeToRecipeCommand
    ) {
        log.debug("Recipe Service Created!!!");
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        log.debug(String.valueOf(recipeRepository.hashCode()));
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Implementing the service...");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        if(id == null) return null;
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        if(detachedRecipe != null) {
            Recipe savedRecipe = recipeRepository.save(detachedRecipe);
            log.debug("Saved Recipe with Id: " + savedRecipe.getId());
            return recipeToRecipeCommand.convert(savedRecipe);
        }
        return null;
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(getRecipeById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

}
