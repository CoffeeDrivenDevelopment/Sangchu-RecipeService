package com.cdd.recipeservice.recipemodule.recipe.domain;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.cdd.recipeservice.global.domain.BaseTime;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientType;

import jakarta.persistence.*;
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
@Table(name = "recipe_tbl", uniqueConstraints = {
	@UniqueConstraint(name = "RECIPE_SERIAL_NUMBER_UNIQUE",
		columnNames = "recipe_serial_number")
})
public class Recipe extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private Integer id;

	@Column(name = "recipe_serial_number")
	private Integer serialNumber;

	@Column(name = "recipe_title")
	private String title;

	@Column(name = "recipe_image")
	private String image;

	@Enumerated(EnumType.STRING)
	@Column(name = "recipe_type")
	private RecipeType type;

	@Enumerated(EnumType.STRING)
	@Column(name = "food_style")
	private FoodStyle foodStyle;

	@Enumerated(EnumType.STRING)
	@Column(name = "main_ingredient_type")
	private IngredientType ingredientType;

	@Enumerated(EnumType.STRING)
	@Column(name = "food_category")
	private RecipeCategory recipeCategory;

	@Column(name = "food_portion")
	private String portion;

	@Column(name = "cooking_time")
	private String cookingTime;

	@Column(name = "cooking_difficulty")
	private String cookingDifficulty;

	@Length(max = 1000)
	@Column(name = "ingredients")
	private String ingredients;

	public List<String> getTag() {
		return List.of(
			getFoodStyle().getDesc(),
			getIngredientType().getDesc(),
			getRecipeCategory().getDesc()
		);
	}
}
