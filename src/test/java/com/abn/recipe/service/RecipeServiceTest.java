package com.abn.recipe.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abn.recipe.exception.RecipeNotFoundException;
import com.abn.recipe.model.Recipe;
import com.abn.recipe.repository.RecipeRepository;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private RecipeService recipeService;

	@Test
	 void testAddRecipe() {
		Recipe recipe = new Recipe(1L, "New Recipe", Arrays.asList("Ingredient1", "Ingredient2"), false, 4,
				"Instructions");
		when(recipeRepository.save(recipe)).thenReturn(recipe);
		Recipe createdRecipe = recipeService.createRecipe(recipe);
		assertEquals(recipe.getName(), createdRecipe.getName());
		verify(recipeRepository, times(1)).save(recipe);
	}

	@Test
	 void testUpdateRecipe_whenReceipeIsPresentInDb() {
		Recipe existingRecipe = new Recipe(1L, "Existing Recipe", Arrays.asList("Ingredient1", "Ingredient2"), false, 4,
				"Instructions");
		Recipe updatedRecipe = new Recipe(1L, "Updated Recipe", Arrays.asList("Ingredient1", "Ingredient2"), false, 4,
				"Updated Instructions");
		when(recipeRepository.existsById(1L)).thenReturn(true);
		when(recipeRepository.save(existingRecipe)).thenReturn(updatedRecipe);
		Recipe result = recipeService.updateRecipe(1L, existingRecipe).get();
		assertEquals(updatedRecipe.getName(), result.getName());
		assertEquals(updatedRecipe.getInstructions(), result.getInstructions());
		verify(recipeRepository, times(1)).save(existingRecipe);
	}
	@Test
	 void testUpdateRecipe_whenReceipeIsNotPresentInDb() {
		Recipe existingRecipe = new Recipe(1L, "Existing Recipe", Arrays.asList("Ingredient1", "Ingredient2"), false, 4,
				"Instructions");
		when(recipeRepository.existsById(1L)).thenReturn(false);
		assertThrows(RecipeNotFoundException.class, ()-> recipeService.updateRecipe(1L, existingRecipe));
		verify(recipeRepository, times(0)).save(existingRecipe);
	}

	@Test
	 void testDeleteRecipe_whenReceipeExistsInDB() {
		doNothing().when(recipeRepository).deleteById(1L);
		when(recipeRepository.existsById(1L)).thenReturn(true);
		assertTrue(recipeService.deleteRecipe(1L));
		verify(recipeRepository, times(1)).deleteById(1L);
	}
	@Test
	 void testDeleteRecipe_whenReceipeNotExistsInDB() {
		when(recipeRepository.existsById(1L)).thenReturn(false);
		assertThrows(RecipeNotFoundException.class,()->recipeService.deleteRecipe(1L));
		verify(recipeRepository, times(0)).deleteById(1L);
	}
	
	@Test
	 void testgetAllRecipes_shouldReturnAllReceipesReturnByRepository() {
		Recipe recipe = new Recipe(1L, "Vegetarian Pizza", Arrays.asList("Cheese", "Tomato", "Dough"), true, 4,
				"Bake in oven");
		when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.getAllRecipes();
		assertEquals(1, recipes.size());
		assertEquals("Vegetarian Pizza", recipes.get(0).getName());
	}

	@Test
	 void testFindByIsVegetarian() {
		Recipe recipe = new Recipe(1L, "Vegetarian Pizza", Arrays.asList("Cheese", "Tomato", "Dough"), true, 4,
				"Bake in oven");
		when(recipeRepository.findByVegetarian(true)).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(true, null, null, null, null);
		assertEquals(1, recipes.size());
		assertEquals("Vegetarian Pizza", recipes.get(0).getName());
	}

	@Test
	 void testFindByServings() {
		Recipe recipe = new Recipe(1L, "Family Pasta", Arrays.asList("Pasta", "Tomato Sauce", "Cheese"), false, 6,
				"Boil and mix");
		when(recipeRepository.findByServings(6)).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(null, 6, null, null, null);
		assertEquals(1, recipes.size());
		assertEquals(6, recipes.get(0).getServings());
	}

	@Test
	 void testFindByIngredient() {
		Recipe recipe = new Recipe(1L, "Garlic Bread", Arrays.asList("Bread", "Garlic", "Butter"), false, 4,
				"Bake in oven");
		when(recipeRepository.findByIngredient("Garlic")).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(null, null, "Garlic", null, null);
		assertEquals(1, recipes.size());
		assertEquals("Garlic Bread", recipes.get(0).getName());
	}

	@Test
	 void testFindByInstruction() {
		Recipe recipe = new Recipe(1L, "Pasta", Arrays.asList("Pasta", "Sauce"), false, 2, "Boil pasta and add sauce");
		when(recipeRepository.findByInstructionsContaining("Boil")).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(null, null, null, null, "Boil");
		assertEquals(1, recipes.size());
		assertEquals("Pasta", recipes.get(0).getName());
	}

	@Test
	 void testFindByNotContainingIngredient() {
		Recipe recipe = new Recipe(1L, "Fruit Salad", Arrays.asList("Apple", "Banana"), true, 2, "Mix all fruits");
		when(recipeRepository.findByIngredientsNotContaining("Salmon")).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(null, null, null, "Salmon", null);
		assertEquals(1, recipes.size());
		assertEquals("Fruit Salad", recipes.get(0).getName());
	}
	
	
	@Test
	 void testfilterRecipes_whenservingsAndIngredientInclude_isNotNull() {
		Recipe recipe = new Recipe(1L, "Family Pasta", Arrays.asList("Pasta", "Tomato Sauce", "Cheese"), false, 6,
				"Boil and mix");
		when(recipeRepository.findByIngredientAndServings("Cheese",6)).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(null, 6, "Cheese", null, null);
		assertEquals(1, recipes.size());
		assertEquals(6, recipes.get(0).getServings());
	}
	@Test
	 void testfilterRecipes_whenInstructionsTextAndIngredientExclude_isNotNull() {
		Recipe recipe = new Recipe(1L, "Family Pasta", Arrays.asList("Pasta", "Tomato Sauce", "Cheese"), false, 6,
				"Boil and mix");
		when(recipeRepository.findByIngredientsNotContaining(anyString())).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(null, null, null,"Cheese","Boil and mix");
		assertEquals(1, recipes.size());
		assertEquals(6, recipes.get(0).getServings());
	}
	
	@Test
	 void testfilterRecipes_whenNoFilterIsPassed_shouldReturnAllReceipes() {
		Recipe recipe = new Recipe(1L, "Family Pasta", Arrays.asList("Pasta", "Tomato Sauce", "Cheese"), false, 6,
				"Boil and mix");
		when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));
		List<Recipe> recipes = recipeService.filterRecipes(null, null, null, null, null);
		assertEquals(1, recipes.size());
		assertEquals(6, recipes.get(0).getServings());
	}

}
