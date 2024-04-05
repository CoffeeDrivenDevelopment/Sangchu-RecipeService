package com.cdd.recipeservice.recipemodule.recipe.domain;

import com.cdd.recipeservice.global.domain.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecipeLike extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "recipe_like_viewed_at")
    private LocalDateTime viewedAt;
}
