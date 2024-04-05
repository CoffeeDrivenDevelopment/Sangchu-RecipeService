package com.cdd.recipeservice.ingredientmodule.targetprice.application;

public interface TargetPriceSaveService {
	void pickTargetPrice(int memberId, int ingredientId, int targetPrice);

	void updateTargetPriceList();

}
