package com.cdd.recipeservice.recipemodule.review.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdd.recipeservice.recipemodule.review.domain.query.CookEatRepositoryCustom;

public interface CookEatRepository extends JpaRepository<CookEat, Long>, CookEatRepositoryCustom {
	List<CookEat> findByMemberId(Integer memberId);

	@Query("SELECT count(ce.id) "
		+ "FROM CookEat ce "
		+ "JOIN Recipe r "
		+ "ON r.id = ce.recipe.id "
		+ "WHERE r.id = :recipeId")
	long findTotalCountByRecipeId(int recipeId);
}
