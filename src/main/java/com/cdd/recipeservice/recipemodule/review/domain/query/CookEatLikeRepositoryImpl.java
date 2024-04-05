package com.cdd.recipeservice.recipemodule.review.domain.query;

import static com.cdd.recipeservice.recipemodule.review.domain.QCookEatLike.*;

import java.util.List;

import com.cdd.recipeservice.recipemodule.review.domain.CookEatLike;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CookEatLikeRepositoryImpl implements CookEatLikeRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public List<CookEatLike> findCookEatsByIds(
		final List<Long> cookEatIds,
		final int memberId
	) {
		return query.selectFrom(cookEatLike)
			.where(cookEatLike.memberId.eq(memberId)
				.and(cookEatLike.id.in(cookEatIds)))
			.fetch();
	}
}
