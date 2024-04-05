package com.cdd.recipeservice.recipemodule.review.domain.query;

import static com.cdd.recipeservice.recipemodule.recipe.domain.QRecipe.*;
import static com.cdd.recipeservice.recipemodule.review.domain.QCookEat.*;

import java.util.List;
import java.util.Optional;

import com.cdd.recipeservice.recipemodule.review.domain.CookEat;
import com.cdd.recipeservice.recipemodule.review.dto.cond.FindCookEatCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CookEatRepositoryImpl implements CookEatRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public Optional<CookEat> findByIdAsJoin(long id) {
		CookEat findCookEat = query.selectFrom(cookEat)
			.join(cookEat.recipe, recipe).fetchJoin()
			.where(cookEat.id.eq(id))
			.fetchOne();
		return Optional.ofNullable(findCookEat);
	}

	@Override
	public List<CookEat> findCookEatsByCond(FindCookEatCond cond) {
		return query.selectFrom(cookEat)
			.join(cookEat.recipe, recipe)
			.where(recipe.id.eq(cond.recipeId())
				.and(cookEat.id.lt(cond.paging().last())))
			.limit(cond.paging().size())
			.orderBy(cookEat.id.desc())
			.fetch();
	}

	@Override
	public boolean hasMoreCookEatById(
		final Long lastCookEatId,
		final int recipeId
	) {
		Optional<CookEat> optionalCookEat = Optional.ofNullable(query.selectFrom(cookEat)
			.join(cookEat.recipe, recipe)
			.where(recipe.id.eq(recipeId)
				.and(cookEat.id.lt(lastCookEatId)))
			.fetchFirst());
		return optionalCookEat.isPresent();
	}
}
