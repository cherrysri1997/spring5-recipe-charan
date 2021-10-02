package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipe/")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/{id}/show", "/show/{id}"})
    public String getRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "recipe/show";
    }

    @RequestMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeForm";
    }

    @RequestMapping("")
    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/show/" + savedRecipeCommand.getId();
    }

    @RequestMapping({"/{id}/update", "/update/{id}"})
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeForm";
    }

    @RequestMapping({"/{id}/delete", "/delete/{id}"})
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }

}
