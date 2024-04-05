package com.cdd.recipeservice.recipemodule.recipe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdd.recipeservice.recipemodule.recipe.domain.query.RecipeRepositoryCustom;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, RecipeRepositoryCustom {

	@Query("SELECT count(r) " +
		"FROM Recipe r " +
		"WHERE r.title LIKE CONCAT('%', :query, '%')")
	long countBySearchCond(String query);

	@Query("SELECT count(r) " +
		"FROM Recipe r " +
		"WHERE r.recipeCategory = :category")
	long countByCategoryCond(RecipeCategory category);
}
