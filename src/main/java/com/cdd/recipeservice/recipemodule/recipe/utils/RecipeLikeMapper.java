package com.cdd.recipeservice.recipemodule.recipe.utils;

import com.cdd.recipeservice.global.annotation.Mapper;
import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeLike;

@Mapper
public class RecipeLikeMapper {

	public RecipeLike makeJpaEntity(final int memberId, final Recipe recipe) {
		return RecipeLike.builder()
			.memberId(memberId)
			.recipe(recipe)
			.viewedAt(LocalDateTimeUtils.today("Asia/Seoul"))
			.build();
	}
}
