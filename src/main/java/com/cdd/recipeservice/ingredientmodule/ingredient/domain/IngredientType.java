package com.cdd.recipeservice.ingredientmodule.ingredient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IngredientType {
	PROCESSED_FOOD("가공식품류"),
	SEAFOOD("해물류"),
	RICE("쌀"),
	DRIED_SEAFOOD("건어물류"),
	MUSHROOM("버섯류"),
	CHICKEN("닭고기"),
	VEGETABLE("채소류"),
	FLOUR("밀가루"),
	BEANS_NUTS("콩/견과류"),
	FRUIT("과일류"),
	MEAT("육류"),
	EGGS_DAIRY("달걀/유제품"),
	GRAINS("곡류"),
	PORK("돼지고기"),
	BEEF("소고기"),
	OTHER("기타");

	private final String desc;
}
