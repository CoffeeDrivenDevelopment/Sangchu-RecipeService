package com.cdd.recipeservice.recipemodule.recipe.domain.query;

import static com.cdd.recipeservice.recipemodule.recipe.domain.QRecipe.*;
import static com.cdd.recipeservice.recipemodule.recipe.domain.QRecipeLike.*;

import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeLike;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeLikeCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeLikeRepositoryImpl implements RecipeLikeRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public List<RecipeLike> findRecipeLikeByCond(RecipeLikeCond cond) {
		return query.selectFrom(recipeLike)
			.join(recipeLike.recipe, recipe).fetchJoin()
			.where(recipeLike.memberId.eq(cond.memberId())
				.and(recipeLike.recipe.id.in(cond.recipeIds())))
			.fetch();
	}
}
