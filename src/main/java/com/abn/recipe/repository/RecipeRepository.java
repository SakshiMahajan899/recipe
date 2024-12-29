package com.abn.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abn.recipe.model.Recipe;

/**
 * This class is used to interact with Database
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByIsVegetarian(boolean isVegetarian);

    List<Recipe> findByServings(int servings);
    
     
    @Query(value = "SELECT r.* FROM Recipe r LEFT JOIN Recipe_ingredients ri ON r.id = ri.Recipe_id WHERE NOT EXISTS (SELECT 1 FROM Recipe_ingredients ri2 WHERE ri2.Recipe_id = r.id AND ri2.ingredients LIKE %:ingredient%)", nativeQuery = true)
    List<Recipe> findByIngredientsNotContaining(String ingredient);
    
    @Query(value = "SELECT r.* FROM Recipe r JOIN Recipe_ingredients ri ON r.id = ri.Recipe_id WHERE ri.ingredients LIKE %:ingredient%", nativeQuery = true)
    List<Recipe> findByIngredient(@Param("ingredient") String ingredient);
    
    List<Recipe> findByInstructionsContaining(String text);
    
    @Query(value = "SELECT r.* FROM Recipe r JOIN Recipe_ingredients ri ON r.id = ri.Recipe_id WHERE ri.ingredients LIKE %:ingredient% AND r.servings = :servings", nativeQuery = true) 
    List<Recipe> findByIngredientAndServings(@Param("ingredient") String ingredient, @Param("servings") int servings);
    
    @Query(value = "SELECT r.* FROM Recipe r JOIN Recipe_ingredients ri ON r.id = ri.Recipe_id WHERE ri.ingredients LIKE %:ingredient% AND r.instructions LIKE %:instruction%", nativeQuery = true) 
    List<Recipe> findByIngredientAndInstruction(@Param("ingredient") String ingredient, @Param("instruction") String instruction);
}