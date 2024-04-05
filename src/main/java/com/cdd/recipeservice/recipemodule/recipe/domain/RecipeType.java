package com.cdd.recipeservice.recipemodule.recipe.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecipeType {
    FRIED("튀김"),
    PAN_FRIED("부침"),
    GRILLED("굽기"),
    BOILED("끓이기"),
    STIR_FRIED("볶음"),
    OTHER("기타"),
    BRAISED("조림"),
    STEAMED("찜"),
    SEASONED("무침"),
    PICKLED("절임"),
    MIXED("비빔"),
    BLANCHED("데치기"),
    BOILED_SOFT("삶기"),
    RAW("회");

    private final String desc;
}
