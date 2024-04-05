package com.cdd.recipeservice.recipemodule.review.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cdd.recipeservice.recipemodule.review.domain.query.CookEatImageRepositoryCustom;

public interface CookEatImageRepository extends JpaRepository<CookEatImage, Long>, CookEatImageRepositoryCustom {
}
