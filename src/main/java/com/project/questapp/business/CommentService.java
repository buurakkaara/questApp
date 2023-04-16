package com.project.questapp.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.questapp.dataAccess.CommentRepository;
import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	public CommentService(CommentRepository commentRepository,UserService userService,PostService postService) {
		super();
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public List<CommentResponse> getAllCommentsWithParams(Optional<Long> userId, Optional<Long> postId) {
		List<Comment> comments;
		if(userId.isPresent() && postId.isPresent()) {
			comments = commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
		}else if (userId.isPresent()) {
			comments = commentRepository.findByUserId(userId.get());
		}else if (postId.isPresent()) {
			comments = commentRepository.findByPostId(postId.get());
		}else 
			comments = commentRepository.findAll();
			
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());	
	}		

	public Comment getCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createComment(CommentCreateRequest commentCreateRequest) {
		User user = userService.getUserById(commentCreateRequest.getUserId());
		Post post = postService.getPostById(commentCreateRequest.getPostId());
		if (user != null && post != null) {
			Comment comment = new Comment();
			comment.setId(commentCreateRequest.getId());
			comment.setText(commentCreateRequest.getText());
			comment.setUser(user);
			comment.setPost(post);
			comment.setCreateDate(new Date());
			return commentRepository.save(comment);
		}else
			return null;
	}

	public Comment updateComment(Long commentId,CommentUpdateRequest commentUpdateRequest) {
	Optional<Comment> comment = commentRepository.findById(commentId);
		if (comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			commentToUpdate.setText(commentUpdateRequest.getText());
			return commentRepository.save(commentToUpdate);
		}else
			return null;
	}

	public void deleteCommentById(Long commentId) {
		 commentRepository.deleteById(commentId);
	}
	
	
	
}
