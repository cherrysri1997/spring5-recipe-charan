package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"ingredients", "ingredient"})
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping({"/recipe/{id}", "/{id}/recipe"})
    public String getIngredientsListOfRecipe(@PathVariable("id") Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    @RequestMapping("/{ingredientId}/recipe/{recipeId}/show")
    public String showIngredient(@PathVariable Long ingredientId, @PathVariable Long recipeId, Model model) {
        model.addAttribute("ingredient",
                ingredientService.getIngredientCommandByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

    @RequestMapping("/{ingredientId}/recipe/{recipeId}/update")
    public String updateIngredient(@PathVariable Long ingredientId, @PathVariable Long recipeId, Model model) {
        model.addAttribute("ingredient", ingredientService.getIngredientCommandByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientForm";
    }

    @RequestMapping("/update")
    @PostMapping
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug(ingredientCommand.getDescription() + ", " + ingredientCommand.getAmount());
        return "redirect:ingredient/" + savedIngredientCommand.getId() + "/recipe/" + savedIngredientCommand.getRecipeId() + "/show";
    }

    @RequestMapping("/{ingredientId}/recipe/{recipeId}/delete")
    public String deleteIngredient(@PathVariable Long ingredientId, @PathVariable Long recipeId) {
        ingredientService.deleteIngredientByRecipeIdAndIngredientId(recipeId, ingredientId);
        return "redirect:ingredient/recipe/" + recipeId;
    }

}
