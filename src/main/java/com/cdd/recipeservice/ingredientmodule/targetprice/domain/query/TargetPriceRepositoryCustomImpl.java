package com.cdd.recipeservice.ingredientmodule.targetprice.domain.query;

import static com.cdd.recipeservice.ingredientmodule.targetprice.domain.QTargetPrice.targetPrice;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.QTargetPriceInfo;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TargetPriceRepositoryCustomImpl implements TargetPriceRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public boolean existsByMemberIdAndIngredientId(int memberId, int ingredientId) {
		return jpaQueryFactory.selectOne()
			.from(targetPrice)
			.where(targetPrice.memberId.eq(memberId)
				.and(targetPrice.ingredient.id.eq(ingredientId)))
			.fetchFirst() != null;
	}

	@Override
	public List<TargetPriceInfo> countPriceByIngredientId(int ingredientId) {
		return jpaQueryFactory
			.select(new QTargetPriceInfo(
				targetPrice.price,
				targetPrice.count()))
			.from(targetPrice)
			.where(targetPrice.ingredient.id.eq(ingredientId))
			.groupBy(targetPrice.price)
			.orderBy(targetPrice.price.desc())
			.fetch();
	}
}
