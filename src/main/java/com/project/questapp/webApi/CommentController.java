package com.project.questapp.webApi;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.business.CommentService;
import com.project.questapp.entities.Comment;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@GetMapping
	public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
		return commentService.getAllCommentsWithParams(userId,postId);
	}
	
	@GetMapping("/{commentId}")
	public Comment getCommentById(@PathVariable Long commentId) {
		return commentService.getCommentById(commentId);
	}
	
	@PostMapping
	public Comment createComment(@RequestBody CommentCreateRequest commentCreateRequest) {
		return commentService.createComment(commentCreateRequest);
	}
	
	@PutMapping("/{commentId}")
	public Comment updateComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest) {
		return commentService.updateComment(commentId,commentUpdateRequest);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable Long commentId) {
		commentService.deleteCommentById(commentId);
	}
	
}
