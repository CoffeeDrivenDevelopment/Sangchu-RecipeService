package com.cdd.recipeservice.ingredientmodule.targetprice.utils;

import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPrice;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPriceRepository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TargetPriceUtils {
	public static int findByMemberIdAndIngredientId(
		final TargetPriceRepository targetPriceRepository,
		final int memberId,
		final int ingredientId,
		final int orElse
	) {
		return targetPriceRepository.findByMemberIdAndIngredientId(memberId, ingredientId)
			.map(TargetPrice::getPrice)
			.orElse(orElse);
	}
}
