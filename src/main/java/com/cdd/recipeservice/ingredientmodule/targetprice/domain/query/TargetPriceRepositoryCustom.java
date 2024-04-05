package com.cdd.recipeservice.ingredientmodule.targetprice.domain.query;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfo;

public interface TargetPriceRepositoryCustom {
	boolean existsByMemberIdAndIngredientId(int memberId, int ingredientId);

	List<TargetPriceInfo> countPriceByIngredientId(int ingredientId);
}
