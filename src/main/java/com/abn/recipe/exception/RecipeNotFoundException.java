package com.abn.recipe.exception;

public class RecipeNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecipeNotFoundException(String message) {
        super(message);
    }
}
