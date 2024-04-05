package com.cdd.recipeservice.ingredientmodule.targetprice.utils;

import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPrice;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPriceRepository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TargetPriceUtils {
	public static int findByMemberIdAndIngredientId(
		TargetPriceRepository targetPriceRepository,
		int memberId,
		int ingredientId,
	int orElse) {
		return targetPriceRepository.findByMemberIdAndIngredientId(memberId, ingredientId)
			.map(TargetPrice::getPrice)
			.orElse(orElse);
	}
}
