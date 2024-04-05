package com.cdd.recipeservice.ingredientmodule.targetprice.application;

import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceItemListResponse;

public interface TargetPriceLoadService {
	TargetPriceItemListResponse getMyTargetPrice(int memberId);
}
