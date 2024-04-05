package com.cdd.recipeservice.recipemodule.review.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdd.recipeservice.recipemodule.review.domain.query.CookEatLikeRepositoryCustom;

public interface CookEatLikeRepository extends JpaRepository<CookEatLike, Long>, CookEatLikeRepositoryCustom {

	Optional<CookEatLike> findByMemberIdAndCookEat(Long memberId, CookEat cookEat);

}
