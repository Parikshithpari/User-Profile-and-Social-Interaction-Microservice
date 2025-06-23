package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.clientConfig.AuthClient;
import com.example.demo.dto.UsersDTO;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthClient client;
	
	public Optional<UsersDTO> getUserByEmail(String email)
	{
		return client.getUserByEmailId(email);
	}
	
	public UserProfile createUserProfile(String email, UUID userId, String userName, String bio) 
	{
	    Optional<UsersDTO> user = client.getUserByEmailId(email);

	    if (user.isEmpty()) 
	    { 
	        throw new RuntimeException("User not registered, please kindly register.");
	    }
		
		UserProfile newUser = new UserProfile();
		newUser.setUserId(userId);
		newUser.setUserName(userName);
		newUser.setBio(bio);
		
		return userRepo.save(newUser);
	}
	
	@Transactional
	public String followTheUser(String currentUserName, String targetUserName)
	{
		UserProfile user = userRepo.getUserByUserName(currentUserName).orElse(null);
		UserProfile targetUser = userRepo.getUserByUserName(targetUserName).orElse(null);
		
		if(user == null || targetUser == null)
		{
			return "User not Found";
		}
		
		user.getFollowing().add(targetUser);
		targetUser.getFollowers().add(user);
		
		userRepo.save(user);
		userRepo.save(targetUser);
		
		return "You started following "+targetUser.getUserName();
	}
	
	@Transactional
	public String unFollowTheUser(String currentUserName, String targetUserName)
	{
		UserProfile user = userRepo.getUserByUserName(currentUserName).orElse(null);
		UserProfile targetUser = userRepo.getUserByUserName(targetUserName).orElse(null);
		
		if(user == null || targetUser == null)
		{
			return "User not Found";
		}
		
		user.getFollowing().remove(targetUser);
		targetUser.getFollowers().remove(user);
		
		userRepo.save(user);
		userRepo.save(targetUser);
		
		return "You have unfollowed "+targetUser.getUserName();
	}
	
	public Optional<UserProfile> getUsersByUserName(String userName)
	{
		return userRepo.getUserByUserName(userName);
	}
}
