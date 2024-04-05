package com.cdd.recipeservice.recipemodule.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdd.recipeservice.recipemodule.comment.domain.query.CommentRepositoryCustom;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
