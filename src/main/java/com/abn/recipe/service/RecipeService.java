package com.abn.recipe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abn.recipe.exception.RecipeNotFoundException;
import com.abn.recipe.model.Recipe;
import com.abn.recipe.repository.RecipeRepository;





@Service
public class RecipeService {

    
    private final RecipeRepository recipeRepository;
    
    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    
    @Autowired
    public RecipeService( RecipeRepository recipeRepository) {
    	this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(Recipe recipe) {
    	
        log.info("Creating new recipe: {}", recipe.getName());
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> updateRecipe(Long id, Recipe recipe) {
        log.info("Updating recipe with id: {}", id);
        
            if (recipeRepository.existsById(id)) {
                recipe.setId(id);
                return Optional.of(recipeRepository.save(recipe));
            } else {
                log.warn("Recipe with id: {} not found", id);
                throw new RecipeNotFoundException("Recipe with id " + id + " not found");
            }
        
    }

    public boolean deleteRecipe(Long id) {
        log.info("Deleting recipe with id: {}", id);
        
            if (recipeRepository.existsById(id)) {
                recipeRepository.deleteById(id);
                return true;
            } else {
                log.warn("Recipe with id: {} not found", id);
                throw new RecipeNotFoundException("Recipe with id " + id + " not found");
            }
        
    }

    public List<Recipe> getAllRecipes() {
        log.info("Fetching all recipes");
        return recipeRepository.findAll();
        
    }

    public List<Recipe> filterRecipes(Boolean isVegetarian, Integer servings, String ingredientInclude, String ingredientExclude, String instructionsText) {
        log.info("Filtering recipes with parameters - isVegetarian: {}, servings: {}, ingredientInclude: {}, ingredientExclude: {}, instructionsText: {}",
                 isVegetarian, servings, ingredientInclude, ingredientExclude, instructionsText);
        
            if (servings != null && ingredientInclude != null) {
                return recipeRepository.findByIngredientAndServings(ingredientInclude, servings);
            }

            if (instructionsText != null && ingredientExclude != null) {
                List<Recipe> recipe =  recipeRepository.findByIngredientsNotContaining(ingredientExclude);
                return recipe.stream().filter(r->r.getInstructions().contains(instructionsText)).toList();
            }

            if (isVegetarian != null) {
                return recipeRepository.findByVegetarian(isVegetarian);
            }
            if (servings != null) {
                return recipeRepository.findByServings(servings);
            }
            if (ingredientInclude != null) {
                return recipeRepository.findByIngredient(ingredientInclude);
            }
            if (ingredientExclude != null) {
                return recipeRepository.findByIngredientsNotContaining(ingredientExclude);
            }
            if (instructionsText != null) {
                return recipeRepository.findByInstructionsContaining(instructionsText);
            }
            return getAllRecipes();
        
    }
}
