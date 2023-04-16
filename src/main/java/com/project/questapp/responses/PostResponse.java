package com.project.questapp.responses;

import java.util.List;

import com.project.questapp.entities.Post;

import lombok.Data;

@Data
public class PostResponse {

	Long id;
	Long userId;
	String username;
	String title;
	String text;
	List<LikeResponse> postLikes;
	
	public PostResponse(Post entity, List<LikeResponse> likes) {
		this.id=entity.getId();
		this.userId=entity.getUser().getId();
		this.username=entity.getUser().getUsername();
		this.title=entity.getTitle();
		this.text=entity.getText();
		this.postLikes=likes;
	}
	
	
}
