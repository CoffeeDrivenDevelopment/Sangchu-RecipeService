package com.cdd.recipeservice.recipemodule.recipe.domain.query;

import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeLike;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeLikeCond;

public interface RecipeLikeRepositoryCustom {
	List<RecipeLike> findRecipeLikeByCond(RecipeLikeCond cond);
}
