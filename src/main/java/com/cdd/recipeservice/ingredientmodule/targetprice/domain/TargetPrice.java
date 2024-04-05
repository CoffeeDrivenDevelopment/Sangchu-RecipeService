package com.cdd.recipeservice.ingredientmodule.targetprice.domain;

import com.cdd.recipeservice.global.domain.*;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "target_price_tbl")
public class TargetPrice extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "target_price_id")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "ingredient_target_price")
	private Integer price;

	public void updateTargetPrice(int targetPrice) {
		this.price = targetPrice;
	}

	public static TargetPrice of(int memberId, Ingredient ingredient, int targetPrice) {
		return TargetPrice.builder()
			.ingredient(ingredient)
			.memberId(memberId)
			.price(targetPrice)
			.build();
	}
}
