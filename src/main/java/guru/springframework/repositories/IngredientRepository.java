package guru.springframework.repositories;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Ingredient findIngredientByIdAndRecipe(Long id, Recipe recipe);

    void deleteByIdAndRecipe(Long id, Recipe recipe);

}
