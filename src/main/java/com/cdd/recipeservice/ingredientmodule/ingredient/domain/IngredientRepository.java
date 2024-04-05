package com.cdd.recipeservice.ingredientmodule.ingredient.domain;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.query.*;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>, IngredientRepositoryCustom {
	@Query("SELECT i FROM Ingredient i WHERE i.id = :id")
	Optional<Ingredient> findById(@Param("id") int id);

	@Query("SELECT i.id FROM Ingredient i")
	List<Integer> findAllOnlyId();

}
