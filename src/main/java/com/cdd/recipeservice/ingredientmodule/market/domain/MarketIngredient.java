package com.cdd.recipeservice.ingredientmodule.market.domain;

import com.cdd.recipeservice.global.domain.BaseTime;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "market_ingredient_tbl")
public class MarketIngredient extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "market_ingredient_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "market_id")
	private Market market;

	@ManyToOne
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;
}
