package com.project.questapp.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.questapp.dataAccess.CommentRepository;
import com.project.questapp.dataAccess.LikeRepository;
import com.project.questapp.dataAccess.PostRepository;
import com.project.questapp.dataAccess.UserRepository;
import com.project.questapp.entities.User;

@Service
public class UserService {

	private UserRepository userRepository;
	private LikeRepository likeRepository;
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	
	public UserService(UserRepository userRepository, LikeRepository likeRepository,
			CommentRepository commentRepository, PostRepository postRepository) {
		super();
		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);	
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User create(User newUser) {
		return userRepository.save(newUser);
	}

	public User updateUser(Long userId, User newUser) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			User foundUser = user.get();
			foundUser.setUsername(newUser.getUsername());
			foundUser.setPassword(newUser.getPassword());
			//foundUser.setAvatar(newUser.getAvatar());
			userRepository.save(foundUser);
			return foundUser;
		}else {
			return null;
		}
	}

	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
	}

	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds=postRepository.findTopByUserId(userId);
		if (postIds.isEmpty()) 
			return null;
		List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
		List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;
	}

	
	
}
