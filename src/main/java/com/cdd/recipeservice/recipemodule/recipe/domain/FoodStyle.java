package com.cdd.recipeservice.recipemodule.recipe.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodStyle {
    SNACK("간식"),
    DAILY_MEAL("일상"),
    DRINKING_SNACK("술안주"),
    GUEST_ENTERTAINMENT("손님접대"),
    LUNCH_BOX("도시락"),
    NUTRITION_MEAL("영양식"),
    LATE_NIGHT_MEAL("야식"),
    QUICK_MEAL("초스피드"),
    DIET("다이어트"),
    HOLIDAY("명절"),
    OTHER("기타"),
    BABY_FOOD("이유식"),
    HANGOVER("해장");

    private final String desc;
}
