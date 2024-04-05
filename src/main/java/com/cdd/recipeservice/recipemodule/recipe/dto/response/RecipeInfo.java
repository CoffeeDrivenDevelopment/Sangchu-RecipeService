package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.io.Serializable;
import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class RecipeInfo implements Serializable {
	private int id;
	private String name;
	private String img;
	private List<String> tags;
	private String ingredients;

	public static RecipeInfo of(Recipe recipe) {
		return RecipeInfo.builder()
			.id(recipe.getId())
			.name(recipe.getTitle())
			.img(recipe.getImage())
			.tags(recipe.getTag())
			.ingredients(recipe.getIngredients())
			.build();
	}
}
