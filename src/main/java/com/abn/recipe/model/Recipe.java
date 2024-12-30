package com.abn.recipe.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Recipe {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		private String name;
		@ElementCollection
	    private List<String> ingredients;
	    private boolean vegetarian;
	    private int servings;
	    private String instructions;
	    
		public Recipe() {
			super();
		}
		public Recipe(Long id, String name, List<String> ingredients, boolean vegetarian, int servings,
				String instructions) {
			this.id = id;
			this.name = name;
			this.ingredients = ingredients;
			this.vegetarian = vegetarian;
			this.servings = servings;
			this.instructions = instructions;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public List<String> getIngredients() {
			return ingredients;
		}
		public boolean getVegetarian() {
			return vegetarian;
		}
		public int getServings() {
			return servings;
		}
		public String getInstructions() {
			return instructions;
		}
		
	    
	    
}
