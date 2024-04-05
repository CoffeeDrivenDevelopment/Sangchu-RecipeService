package com.cdd.recipeservice.recipemodule.recipe.domain.query;

import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeCategory;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeCategoryCond;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeSearchCond;

public interface RecipeRepositoryCustom {
	List<Recipe> findBySearchCond(RecipeSearchCond cond);

	List<Recipe> findByCategoryCond(RecipeCategoryCond cond);

	boolean hasMoreBySearchCond(String query, int lastId);

	boolean hasMoreByCategoryCond(RecipeCategory category, int lastId);
}
