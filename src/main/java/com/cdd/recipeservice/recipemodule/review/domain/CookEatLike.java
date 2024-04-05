package com.cdd.recipeservice.recipemodule.review.domain;

import com.cdd.recipeservice.global.domain.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cook_eat_like_tbl")
public class CookEatLike extends BaseTime {
    @Id
    @Column(name = "cook_eat_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cook_eat_id")
    private CookEat cookEat;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "cook_eat_like_viewed_at")
    private LocalDateTime viewedAt;
}
