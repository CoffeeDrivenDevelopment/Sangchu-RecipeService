package com.cdd.recipeservice.recipemodule.comment.domain;

import com.cdd.recipeservice.global.domain.BaseTime;
import com.cdd.recipeservice.recipemodule.comment.exception.CommentErrorCode;
import com.cdd.recipeservice.recipemodule.comment.exception.CommentException;
import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment_tbl")
public class Comment extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "comment_content")
	private String content;

	@Column(name = "reply_count")
	private Integer replyCount;

	public void updateReplyCount(int count) {
		replyCount = count;
	}

	public void updateContent(String newContent) {
		if (newContent.length() > 255) {
			throw new CommentException(CommentErrorCode.TOO_LONG_COMMENT);
		}
		content = newContent;
	}

	/**
	 * 댓글을 삭제합니다. <p>
	 * memberId가 `-1`인 것은 주인이 없다는 것을 나타냅니다.
	 */
	public void remove() {
		memberId = -1;
		content = "삭제된 댓글입니다.";
	}
}
