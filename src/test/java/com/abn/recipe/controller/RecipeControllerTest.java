package com.abn.recipe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.abn.recipe.config.SecurityConfig;
import com.abn.recipe.model.Recipe;
import com.abn.recipe.repository.RecipeRepository;
import com.abn.recipe.service.RecipeService;

@WebMvcTest(RecipeController.class)
@ContextConfiguration(classes = { RecipeController.class,SecurityConfig.class })
class RecipeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private RecipeRepository recipeRepository;
	
	@MockBean
	private RecipeService recipeService;

	@Test
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testCreateRecipe() throws Exception {
		String newRecipeJson = "{ \"name\": \"New Recipe\", \"ingredients\": [\"Ingredient1\", \"Ingredient2\"], \"isVegetarian\": true, \"servings\": 4, \"instructions\": \"Mix ingredients and cook.\" }";
		Recipe recipe = new Recipe(1L, "New Recipe", Arrays.asList("Ingredient1", "Ingredient2"), false, 4,
				"Instructions");
		when(recipeRepository.save(recipe)).thenReturn(recipe);
		mockMvc.perform(post("/api/recipes")
				.contentType(MediaType.APPLICATION_JSON).content(newRecipeJson))
				.andExpect(status().isOk());
	}
	@Test 
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testDeleteRecipe_whenReceipeNotExistsInDatabase() throws Exception {
		// Assume a recipe with ID 1 do not exists 
		when(recipeService.deleteRecipe(1L)).thenReturn(false);
		mockMvc.perform(delete("/api/recipes/1")).andExpect(status().isNotFound()); 
		}
	
	@Test 
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testDeleteRecipe_whenReceipeExistInDatabase() throws Exception {
		// Assume a recipe with ID 1  exists 
		when(recipeService.deleteRecipe(1L)).thenReturn(true);
		mockMvc.perform(delete("/api/recipes/1")).andExpect(status().isNoContent()); 
		}
	

	@Test
	 void testUpdateRecipe_whenReceipeIsPresentInDatabase() throws Exception { 
		String updatedRecipeJson = "{ \"id\": 1,\"name\": \" Recipe\", \"ingredients\": [\"Ingredient1\", \"Ingredient2\"], \"isVegetarian\": true, \"servings\": 4, \"instructions\": \"Mix ingredients and cook.\" }";
		Recipe updatedRecipe = new Recipe(1L, "Updated Recipe", Arrays.asList("Ingredient1","Ingredient2"), true, 4,
				"Mix ingredients and cook.");
		when(recipeService.updateRecipe(anyLong(),any(Recipe.class))).thenReturn(Optional.of(updatedRecipe));
		  mockMvc.perform(put("/api/recipes/1")
				  .with(httpBasic("admin", "admin")).contentType(MediaType.APPLICATION_JSON).content(updatedRecipeJson)).andExpect(status().isOk())
														 .andExpect(jsonPath("$.name").value("Updated Recipe"))
														 .andExpect(jsonPath("$.servings").value(4)); 
	
	}
	@Test
	 void testUpdateRecipe_whenReceipeIsNotPresentInDatabase() throws Exception { 
		String updatedRecipeJson = "{ \"id\": 1,\"name\": \" Recipe\", \"ingredients\": [\"Ingredient1\", \"Ingredient2\"], \"isVegetarian\": true, \"servings\": 4, \"instructions\": \"Mix ingredients and cook.\" }";
		when(recipeService.updateRecipe(anyLong(),any(Recipe.class))).thenReturn(Optional.empty());
		  mockMvc.perform(put("/api/recipes/1")
				  .with(httpBasic("admin", "admin")).contentType(MediaType.APPLICATION_JSON).content(updatedRecipeJson)).andExpect(status().isNotFound());

	
	}

	@Test
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testGetAllRecipes() throws Exception {
		Recipe recipe = new Recipe(1L, "Spaghetti", Arrays.asList("Pasta", "Tomato Sauce"), false, 2,
				"Boil pasta and add sauce");
		when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(recipe));
		mockMvc.perform(get("/api/recipes")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Spaghetti"));
	}

	@Test
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testFilterByIsVegetarian() throws Exception {
		Recipe recipe = new Recipe(1L, "Vegetarian Pizza", Arrays.asList("Cheese", "Tomato", "Dough"), true, 4,
				"Bake in oven");
		when(recipeService.filterRecipes(true, null, null, null, null)).thenReturn(Arrays.asList(recipe));
		mockMvc.perform(get("/api/recipes/filter").param("isVegetarian", "true")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Vegetarian Pizza"));
	}

	@Test
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testFilterByServings() throws Exception {
		Recipe recipe = new Recipe(1L, "Family Pasta", Arrays.asList("Pasta", "Tomato Sauce", "Cheese"), false, 6,
				"Boil and mix");
		when(recipeService.filterRecipes(null, 6, null, null, null)).thenReturn(Arrays.asList(recipe));
		mockMvc.perform(get("/api/recipes/filter").param("servings", "6")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Family Pasta"));
	}

	@Test
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testFilterByIngredient() throws Exception {
		Recipe recipe = new Recipe(1L, "Garlic Bread", Arrays.asList("Bread", "Garlic", "Butter"), false, 4,
				"Bake in oven");
		when(recipeService.filterRecipes(null, null, "Garlic", null, null)).thenReturn(Arrays.asList(recipe));
		mockMvc.perform(get("/api/recipes/filter").param("ingredientInclude", "Garlic")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Garlic Bread"));
	}

	@Test
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testFilterByInstruction() throws Exception {
		Recipe recipe = new Recipe(1L, "Pasta", Arrays.asList("Pasta", "Sauce"), false, 2, "Boil pasta and add sauce");
		when(recipeService.filterRecipes(null, null, null, null, "Boil")).thenReturn(Arrays.asList(recipe));
		mockMvc.perform(get("/api/recipes/filter").param("instructionsText", "Boil")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Pasta"));
	}

	@Test
	@WithMockUser(username = "admin",password = "admin", roles = "ADMIN")
	 void testFilterByNotContainingIngredient() throws Exception {
		Recipe recipe = new Recipe(1L, "Fruit Salad", Arrays.asList("Apple", "Banana"), true, 2, "Mix all fruits");
		when(recipeService.filterRecipes(null, null, null, "Salmon", null)).thenReturn(Arrays.asList(recipe));
		mockMvc.perform(get("/api/recipes/filter").param("ingredientExclude", "Salmon")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Fruit Salad"));
	}
}
