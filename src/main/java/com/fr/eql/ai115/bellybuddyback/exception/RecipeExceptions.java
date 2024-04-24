package com.fr.eql.ai115.bellybuddyback.exception;

public class RecipeExceptions extends RuntimeException{


  public static class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(String ingredient) {
      super("Ingredient not found: " + ingredient);
    }
  }

  public static class NoRandomRecipeFoundException extends RuntimeException {
    public NoRandomRecipeFoundException() {
      super("No random recipe found");
    }
  }
}
