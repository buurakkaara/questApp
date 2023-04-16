package com.project.questapp.responses;

import com.project.questapp.entities.Comment;

import lombok.Data;

@Data
public class CommentResponse {

	Long id;
	Long userId;
	String text;
	
	
	public CommentResponse(Comment entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.text = entity.getText();
	}
	
	
	
}
