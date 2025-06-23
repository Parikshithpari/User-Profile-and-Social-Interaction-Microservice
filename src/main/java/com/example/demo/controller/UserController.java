package com.example.demo.controller;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.UsersDTO;
import com.example.demo.model.UserProfile;
import com.example.demo.service.UserService;

@RestController
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/getUserByEmail/{email}")
	public Optional<UsersDTO> getUserByEmailId(@PathVariable String email)
	{
		return userService.getUserByEmail(email);
	}
	
	@PostMapping("/create")
	public ResponseEntity<UserProfile> createUserProfile(
			@RequestParam String email,
			@RequestParam UUID userId,
			@RequestParam String userName,
			@RequestParam String bio)
	{
		UserProfile profile = userService.createUserProfile(email, userId, userName, bio);
		return ResponseEntity.ok(profile);
	}
	
	@PostMapping("/follow/{currentUserName}/{targetUserName}")
	public ResponseEntity<String> followUser(@PathVariable String currentUserName, @PathVariable String targetUserName)
	{
		String response = userService.followTheUser(currentUserName, targetUserName);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/unfollow/{currentUserName}/{targetUserName}")
	public ResponseEntity<String> unFollowUser(@PathVariable String currentUserName, @PathVariable String targetUserName)
	{
		String response = userService.unFollowTheUser(currentUserName, targetUserName);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getUser/{userName}")
	public Optional<UserProfile> getUserByName(@PathVariable String userName)
	{
		return userService.getUsersByUserName(userName);
	}
}

