package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain;

import com.cdd.recipeservice.global.domain.BaseTime;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ingredient_sales_daily_price_stats_tbl")
public class IngredientSalesDailyPriceStat extends BaseTime implements PriceCalculator{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_sales_daily_price_stats_id")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	@Column(name = "max_price")
	private Integer maxPrice;

	@Column(name = "avg_price")
	private Integer avgPrice;

	@Column(name = "min_price")
	private Integer minPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "market_type")
	private MarketType marketType;

	public IngredientSalesDailyPriceStat(Integer maxPrice, Integer avgPrice, Integer minPrice) {
		this.maxPrice = maxPrice;
		this.avgPrice = avgPrice;
		this.minPrice = minPrice;
	}

	public IngredientSalesDailyPriceStat(Integer avgPrice) {
		this.avgPrice = avgPrice;
	}

	@Override
	public int getPrice() {
		return getAvgPrice();
	}
}
