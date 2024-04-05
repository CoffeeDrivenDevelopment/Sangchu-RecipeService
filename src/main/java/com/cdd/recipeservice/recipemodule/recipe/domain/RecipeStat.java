package com.cdd.recipeservice.recipemodule.recipe.domain;

import com.cdd.recipeservice.global.domain.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "recipe_stats_tbl")
public class RecipeStat extends BaseTime {
	@Id
	private Integer id;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@Column(name = "likes_count")
	private Integer likes;

	@Column(name = "comments_count")
	private Integer comments;

	@Column(name = "cook_eat_count")
	private Integer reviews;
}
