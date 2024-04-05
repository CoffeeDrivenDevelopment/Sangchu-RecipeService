package com.cdd.recipeservice.recipemodule.recipe.domain.query;

import static com.cdd.recipeservice.recipemodule.recipe.domain.QRecipe.*;

import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeCategory;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeCategoryCond;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeSearchCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public List<Recipe> findByCategoryCond(RecipeCategoryCond cond) {
		return query.selectFrom(recipe)
			.where(recipe.recipeCategory.eq(cond.category()))
			.where(recipe.id.lt(cond.pagingCond().last()))
			.orderBy(recipe.id.desc())
			.limit(cond.pagingCond().size())
			.fetch();
	}

	@Override
	public List<Recipe> findBySearchCond(RecipeSearchCond cond) {
		return query.selectFrom(recipe)
			.where(recipe.title.contains(cond.query()))
			.where(recipe.id.lt(cond.pagingCond().last()))
			.orderBy(recipe.id.desc())
			.limit(cond.pagingCond().size())
			.fetch();
	}

	@Override
	public boolean hasMoreByCategoryCond(
		final RecipeCategory category,
		final int lastId
	) {
		return query.selectFrom(recipe)
			.where(recipe.recipeCategory.eq(category))
			.where(recipe.id.lt(lastId))
			.fetchFirst()
			!= null;
	}

	@Override
	public boolean hasMoreBySearchCond(
		final String q,
		final int lastId
	) {
		return query.selectFrom(recipe)
			.where(recipe.title.like(q))
			.where(recipe.id.lt(lastId))
			.fetchFirst()
			!= null;
	}
}
