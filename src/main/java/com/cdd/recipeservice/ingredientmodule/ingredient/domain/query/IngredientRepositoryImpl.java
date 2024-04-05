package com.cdd.recipeservice.ingredientmodule.ingredient.domain.query;

import static com.cdd.recipeservice.ingredientmodule.ingredient.domain.QIngredient.ingredient;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientType;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IngredientRepositoryImpl implements IngredientRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public IngredientInxInfo getRangeIngredientId() {
		return jpaQueryFactory
			.select(new QIngredientInxInfo(
				ingredient.id.min(),
				ingredient.id.count()
			))
			.from(ingredient)
			.fetchOne();
	}

	@Override
	public List<IngredientCategory> findAllCategory() {
		return jpaQueryFactory.select(new QIngredientCategory(ingredient.id, ingredient.name))
			.from(ingredient)
			.fetch();
	}

	@Override
	public List<IngredientSearch> findIngredient(List<IngredientType> categories, String ingredientName) {
		return categories == null
			? jpaQueryFactory.select(new QIngredientSearch(ingredient.id, ingredient.name, ingredient.img))
			.from(ingredient)
			.where(ingredient.name.like("%" + ingredientName + "%"))
			.fetch()
			: jpaQueryFactory.select(new QIngredientSearch(ingredient.id, ingredient.name, ingredient.img))
			.from(ingredient)
			.where(ingredient.name.like("%" + ingredientName + "%")
				.and(ingredient.code.in(categories)))
			.fetch();
	}

	@Override
	public List<IngredientPopular> findPopularIngredient() {
		return jpaQueryFactory.select(new QIngredientPopular(ingredient.id, ingredient.name, ingredient.img))
			.from(ingredient)
			.where(ingredient.name.ne("비누")
				.and(ingredient.name.ne("치약"))
				.and(ingredient.name.ne("칫솔"))
				.and(ingredient.name.ne("샴푸"))
				.and(ingredient.name.ne("바디워시"))
				.and(ingredient.name.ne("")))
			.orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
			.limit(4)
			.fetch();
	}
}
