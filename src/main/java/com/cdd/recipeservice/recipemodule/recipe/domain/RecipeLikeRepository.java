package com.cdd.recipeservice.recipemodule.recipe.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdd.recipeservice.recipemodule.recipe.domain.query.RecipeLikeRepositoryCustom;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long>, RecipeLikeRepositoryCustom {
	boolean existsByMemberIdAndRecipe(int memberId, Recipe recipe);

	Optional<RecipeLike> findRecipeLikeByMemberIdAndRecipe(int memberId, Recipe recipe);

	@Query(
		value = "SELECT r " +
			"FROM RecipeLike rl " +
			"JOIN Recipe r " +
			"ON rl.recipe.id = r.id " +
			"WHERE rl.memberId = :memberId",
		countQuery =
			"SELECT COUNT(rl) "
				+ "FROM RecipeLike rl "
				+ "where rl.memberId = :memberId")
	Page<Recipe> findByMemberId(@Param("memberId") int memberId, Pageable pageable);
}
