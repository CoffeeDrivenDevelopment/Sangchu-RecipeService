package com.cdd.recipeservice.recipemodule.review.domain.query;

import static com.cdd.recipeservice.recipemodule.review.domain.QCookEatImage.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CookEatImageRepositoryCustomImpl implements CookEatImageRepositoryCustom{
	private final JPAQueryFactory jpaQueryFactory;
	@Override
	public String findImageByCookEatId(Long cookEatId){
		return jpaQueryFactory.select(cookEatImage.image)
			.from(cookEatImage)
			.where(cookEatImage.cookEat.id.eq(cookEatId))
			.fetchFirst();
	}
}
