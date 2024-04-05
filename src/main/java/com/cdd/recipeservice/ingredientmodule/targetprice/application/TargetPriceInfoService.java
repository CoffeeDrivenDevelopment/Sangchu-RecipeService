package com.cdd.recipeservice.ingredientmodule.targetprice.application;

import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfoResponse;

public interface TargetPriceInfoService {
	TargetPriceInfoResponse getTargetPriceInfoList(int memberId, int ingredientId);
}
