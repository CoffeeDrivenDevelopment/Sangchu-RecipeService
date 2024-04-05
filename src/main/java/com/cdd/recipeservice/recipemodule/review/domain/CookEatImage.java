package com.cdd.recipeservice.recipemodule.review.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cook_eat_image_tbl")
public class CookEatImage {
	@Id
	@Column(name = "cook_eat_image_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cook_eat_image")
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cook_eat_id")
	private CookEat cookEat;

	public static CookEatImage from(String image) {
		return CookEatImage.builder()
			.image(image)
			.build();
	}

	public void updateCookEat(CookEat cookEat) {
		this.cookEat = cookEat;
	}
}
