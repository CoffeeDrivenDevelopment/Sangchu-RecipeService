package com.cdd.recipeservice.ingredientmodule.ingredient.domain;

import com.cdd.recipeservice.global.domain.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "ingredient_tbl",
	uniqueConstraints = {
		@UniqueConstraint(name = "NAME_UNIQUE",
			columnNames = "ingredient_name")
	})
public class Ingredient extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id")
	private Integer id;

	@Column(name = "ingredient_name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "classification_code")
	private IngredientType code;

	@Column(name = "ingredient_image")
	private String img;

	@Column(name = "ingredient_volume")
	private Integer volume;

	@Column(name = "ingredient_unit")
	private String unit;

	@Column(name = "know_how")
	private String knowHow;

	public void update(String knowHow) {
		this.knowHow = knowHow;
	}
}
