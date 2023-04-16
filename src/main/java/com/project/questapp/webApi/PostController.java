package com.project.questapp.webApi;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.business.PostService;
import com.project.questapp.entities.Post;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;

@RestController
@RequestMapping("/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	@GetMapping
	@Transactional
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
		return postService.getAllPosts(userId);
	}
	
	@GetMapping("/{postId}")
	public PostResponse getPostById(@PathVariable Long postId){
		return postService.getPostByIdWithLikes(postId);
	}
	
	@PostMapping
	public Post createPost(@RequestBody PostCreateRequest newPostRequest) {
		return postService.createPost(newPostRequest);
	}
	
	@PutMapping("/{postId}")
	public Post updatePost(@PathVariable Long postId , @RequestBody PostUpdateRequest postUpdateRequest) {
		return postService.updatePostById(postId,postUpdateRequest);
	}
	
	@DeleteMapping("/{postId}")
	public void deletePost(@PathVariable Long postId){
		postService.deletePostById(postId);
	}
	
}
