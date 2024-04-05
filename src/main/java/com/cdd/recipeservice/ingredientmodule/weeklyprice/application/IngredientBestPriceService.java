package com.cdd.recipeservice.ingredientmodule.weeklyprice.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cdd.recipeservice.ingredientmodule.market.domain.Market;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredient;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredientRepository;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredientSalesPrice;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredientSalesPriceRepository;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketIngredientBestPrice;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketIngredientBestPriceResponse;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeErrorCode;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IngredientBestPriceService {
	private final MarketRepository marketRepository;
	private final MarketIngredientRepository marketIngredientRepository;
	private final MarketIngredientSalesPriceRepository marketIngredientSalesPriceRepository;

	public MarketIngredientBestPriceResponse getOnlineMarketBestPrice(int ingredientId) {
		List<MarketIngredient> marketIngredientList = marketIngredientRepository.findByIngredientIdToday(ingredientId);
		List<MarketIngredientBestPrice> bestPriceList = new ArrayList<>();
		for (MarketIngredient marketIngredient : marketIngredientList) {
			Market market = marketRepository.findById(marketIngredient.getMarket().getId())
				.orElseThrow(() -> new RecipeException(RecipeErrorCode.NO_EXISTED_MARKET));
			if(market.getType().equals(MarketType.OFFLINE)){
				continue;
			}
			MarketIngredientSalesPrice marketIngredientSalesPrice = marketIngredientSalesPriceRepository.findMarketIngredientSalesPriceByToday(
					marketIngredient.getId())
				.orElseThrow(() -> new RecipeException(RecipeErrorCode.NO_EXISTED_MARKET_INGREDIENT_SALES_PRICE));
			bestPriceList.add(
				MarketIngredientBestPrice.builder()
					.marketName(market.getName())
					.marketLink(marketIngredientSalesPrice.getSalesLink())
					.price(marketIngredientSalesPrice.getPrice())
					.build()
			);
		}
		return MarketIngredientBestPriceResponse.from(bestPriceList);
	}
}
