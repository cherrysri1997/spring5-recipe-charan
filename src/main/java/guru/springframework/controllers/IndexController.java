package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by jt on 6/1/17.
 */
@Controller
@Slf4j
public class IndexController {

//    private final CategoryRepository categoryRepository;
//    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeService recipeService;
//    private final RecipeRepository recipeRepository;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
//        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
//        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Ounce");
//        categoryOptional.ifPresent(category -> System.out.println("Cat id is: " + category.getId()));
//        unitOfMeasureOptional.ifPresent(unitOfMeasure -> System.out.println("UOM id id: " + unitOfMeasure.getId()));
//        recipeRepository.findById(1L).ifPresent(recipe -> model.addAttribute("recipe", recipe));
//        List<Recipe> recipes = new ArrayList<>();
//        recipeRepository.findAll().forEach(recipes::add);
        log.debug("Loading Index Page...");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
