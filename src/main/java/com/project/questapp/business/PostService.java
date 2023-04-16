package com.project.questapp.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.questapp.dataAccess.PostRepository;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.LikeResponse;
import com.project.questapp.responses.PostResponse;

@Service
public class PostService {

	private PostRepository postRepository;
	private LikeService likeService;
	private UserService userService;
	
	public PostService(PostRepository postRepository,UserService userService) {
		super();
		this.postRepository = postRepository;
		this.userService = userService;	
	}

	@Autowired
	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}
	
	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if(userId.isPresent()) {
			list = postRepository.findByUserId(userId.get());
	}
		list = postRepository.findAll();
		return list.stream().map(p -> {
			List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
			return new PostResponse(p,likes);}).collect(Collectors.toList());
		}

	public Post getPostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}

	public PostResponse getPostByIdWithLikes(Long postId) {
		Post post = postRepository.findById(postId).orElse(null);
		List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(postId));
		return new PostResponse(post,likes); 
	}
	
	public Post createPost(PostCreateRequest newPostRequest) {
		User user = userService.getUserById(newPostRequest.getUserId());
		if(user == null)
			return null;
		Post post = new Post();
		post.setId(newPostRequest.getId());
		post.setText(newPostRequest.getText());
		post.setTitle(newPostRequest.getTitle());
		post.setUser(user);
		post.setCreateDate(new Date());
		return postRepository.save(post);
	}

	public Post updatePostById(Long postId,PostUpdateRequest postUpdateRequest) {
		Optional<Post> post = postRepository.findById(postId);
		if(post.isPresent()) {
			Post toUpdate = post.get();
			toUpdate.setText(postUpdateRequest.getText());
			toUpdate.setTitle(postUpdateRequest.getTitle());
			postRepository.save(toUpdate);
			return toUpdate;
		}		
		return null;
	}

	public void deletePostById(Long postId) {
		postRepository.deleteById(postId);
	}

}
