package com.cdd.recipeservice.ingredientmodule.market.domain;

import org.hibernate.validator.constraints.*;

import com.cdd.recipeservice.global.domain.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Entity
@Table(name = "market_ingredient_sales_price_tbl")
public class MarketIngredientSalesPrice extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "market_ingredient_sales_price_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "market_ingredient_id")
	private MarketIngredient marketIngredient;

	@Column(name = "market_ingredient_sales_price")
	private Integer price;

	@Column(name = "market_ingredient_sales_volume")
	private Integer volume;

	@Length(max = 10)
	@Column(name = "market_ingredient_sales_unit")
	private String unit;

	@NotNull
	@Length(max = 255)
	@Column(name = "sales_link")
	private String salesLink;

}
