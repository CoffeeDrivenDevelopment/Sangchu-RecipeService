package com.cdd.recipeservice.recipemodule.recipe.dto.cond;

import java.util.List;

public record RecipeLikeCond(
	int memberId,
	List<Integer> recipeIds
) {
}
