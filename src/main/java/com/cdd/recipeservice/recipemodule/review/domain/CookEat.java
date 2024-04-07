package com.cdd.recipeservice.recipemodule.review.domain;

import java.util.List;

import com.cdd.recipeservice.global.domain.BaseTime;
import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cook_eat_tbl")
public class CookEat extends BaseTime {
	@Id
	@Column(name = "cook_eat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "title")
	private String title;

	@NotNull
	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "cook_eat_id")
	private List<CookEatImage> images;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "cook_eat_id")
	private List<CookEatLike> likes;

	public void addImage(CookEatImage image) {
		images.add(image);
		image.updateCookEat(this);
	}
}
