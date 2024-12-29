package com.abn.recipe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abn.recipe.model.Recipe;
import com.abn.recipe.service.RecipeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** * REST controller for managing recipes. */ 
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
	
	
	private static final Logger log = LoggerFactory.getLogger(RecipeController.class);
	private final RecipeService recipeService;

    @Autowired
    public RecipeController( RecipeService recipeService) {
    	this.recipeService = recipeService;
    }

    
    /** 
     *  Create a new recipe. 
     *  
     *  @param recipe the recipe to create 
     *  @return ResponseEntity with status 200 (OK) and the created recipe 
     */
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
    	log.info("Request to create recipe: {}", recipe.getName());
        return ResponseEntity.ok(recipeService.createRecipe(recipe));
    }

    
    /** 
     *  Update an existing recipe.
     * @param id the id of the recipe to update
     * @param recipe the updated recipe
     * @return ResponseEntity with status 200 (OK) and the updated recipe, or status 404 (Not Found) if the recipe does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
    	log.info("Request to update recipe with id: {}", id);
        Optional<Recipe> updatedRecipe = recipeService.updateRecipe(id, recipe);
        return updatedRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    /**
     * Delete a recipe by id. 
     * @param id the id of the recipe to delete 
     * @return ResponseEntity with status 204 (No Content) if deletion is successful, or status 404 (Not Found) if the recipe does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
    	log.info("Request to delete recipe with id: {}", id);
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.noContent().build();
        }
        log.warn("Recipe with id: {} not found", id);
        return ResponseEntity.notFound().build();
    }

    /**
     * 
     * Get all recipes.
     * 
     * @return List of all recipes
     */
    @GetMapping
    public List<Recipe> getAllRecipes() {
    	log.info("Request to get all recipes");
        return recipeService.getAllRecipes();
    }

    /** 
     * 
     * 
     * Filter recipes based on various parameters. 
     * @param isVegetarian filter by vegetarian status 
     * @param servings filter by number of servings 
     * @param ingredientInclude include recipes with this ingredient 
     * @param ingredientExclude exclude recipes with this ingredient 
     * @param instructionsText filter by instructions text 
     * @return List of filtered recipes 
     * 
     * */
    @GetMapping("/filter")
    public List<Recipe> filterRecipes(@RequestParam(required = false) Boolean isVegetarian,
                                      @RequestParam(required = false) Integer servings,
                                      @RequestParam(required = false) String ingredientInclude,
                                      @RequestParam(required = false) String ingredientExclude,
                                      @RequestParam(required = false) String instructionsText) {
    	log.info("Request to filter recipes with parameters - isVegetarian: {}, servings: {}, ingredientInclude: {}, ingredientExclude: {}, instructionsText: {}", 
    			isVegetarian, servings, ingredientInclude, ingredientExclude, instructionsText);
        return recipeService.filterRecipes(isVegetarian, servings, ingredientInclude, ingredientExclude, instructionsText);
    }
}

