package com.cdd.recipeservice.ingredientmodule.targetprice.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdd.recipeservice.ingredientmodule.targetprice.domain.query.TargetPriceRepositoryCustom;

@Repository
public interface TargetPriceRepository extends JpaRepository<TargetPrice, Long>, TargetPriceRepositoryCustom {
	@Query("SELECT tp " +
		"FROM TargetPrice tp " +
		"WHERE tp.ingredient.id = :ingredientId " +
		"And tp.memberId = :memberId")
	Optional<TargetPrice> findByMemberIdAndIngredientId(
		@Param("memberId") int memberId,
		@Param("ingredientId") int ingredientId);

	@Query("SELECT tp " +
		"FROM TargetPrice tp " +
		"WHERE tp.memberId = :memberId")
	List<TargetPrice> findByMemberId(
		@Param("memberId") int memberId);
}
