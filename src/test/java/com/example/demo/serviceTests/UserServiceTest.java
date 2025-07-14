package com.example.demo.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import com.example.demo.clientConfig.AuthClient;
import com.example.demo.dto.UsersDTO;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest 
{
	@InjectMocks
	private UserService service;
	
	@Mock
	private AuthClient clientRepo;
	
	@Mock
	private UserRepository repo;
	
	@Test
	void GettingTheUserByEmailTest()
	{
		String email = "testMail@mail.com";
		UsersDTO mockUser = new UsersDTO();
		
		mockUser.setEmail(email);
		
		when(clientRepo.getUserByEmailId(email)).thenReturn(Optional.of(mockUser));
		
		Optional<UsersDTO> users = clientRepo.getUserByEmailId(email);
		
		assertTrue(users.isPresent());
		assertEquals(email, users.get().getEmail());
		verify(clientRepo).getUserByEmailId(email);
	}
	
	@Test
	void GetTheUserByTheirUserName()
	{
		String userName = "testUserName";
		UserProfile mockUser = new UserProfile();
		mockUser.setUserName(userName);
		
		when(repo.getUserByUserName(userName)).thenReturn(Optional.of(mockUser));
		
		Optional<UserProfile> users = service.getUsersByUserName(userName);
		
		assertTrue(users.isPresent());
		assertEquals(userName, users.get().getUserName());
		verify(repo).getUserByUserName(userName);
	}
	
	@Test
	void RegisteringTheUserTest()
	{
		UUID userId = UUID.randomUUID();
		String userName = "testUserName";
		String testBio = "bio";
		
		String email = "testEmail@mail.com";
		
		UsersDTO mockUser = new UsersDTO();
		when(clientRepo.getUserByEmailId(email)).thenReturn(Optional.of(mockUser));
		
		UserProfile actualUser = new UserProfile();
		actualUser.setUserId(userId);
		actualUser.setUserName(userName);
		actualUser.setBio(testBio);
		
		when(repo.save(any(UserProfile.class))).thenReturn(actualUser);
		
		UserProfile saveUser = service.createUserProfile(email, userId, userName, testBio);
		
		assertNotNull(saveUser);
		assertEquals(userName, saveUser.getUserName());
		assertEquals(userId, saveUser.getUserId());
		assertEquals("bio", saveUser.getBio());
		verify(clientRepo).getUserByEmailId(email);
		verify(repo).save(any(UserProfile.class));
	}
	
	@Test
	void shouldThrowTheRunTimeExceptionWhenUserIsNotPresent()
	{
		UUID userId = UUID.randomUUID();
		String email = "testMail@mail.com";
		
		when(clientRepo.getUserByEmailId(email)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, () ->
		{
			UserProfile user = service.createUserProfile(email, userId, "testName", "bio");
		});
		
		assertEquals("User not registered, please kindly register.", exception.getMessage());
		verify(clientRepo).getUserByEmailId(email);
		verifyNoInteractions(repo);
	}
	
	@Test
	void FollowTheAnotherUserTest()
	{
		String currentUserName = "testUser1";
		String targetUserName = "testUser2";
		
		UserProfile currentUser = new UserProfile();
		currentUser.setUserName(currentUserName);
		currentUser.setFollowers(new HashSet<>());
		currentUser.setFollowing(new HashSet<>());
		
		UserProfile targetUser = new UserProfile();
		targetUser.setUserName(targetUserName);
		targetUser.setFollowers(new HashSet<>());
		targetUser.setFollowing(new HashSet<>());
		
		when(repo.getUserByUserName(currentUserName)).thenReturn(Optional.of(currentUser));
		when(repo.getUserByUserName(targetUserName)).thenReturn(Optional.of(targetUser));
		
		String result = service.followTheUser(currentUserName, targetUserName);
		
		assertEquals("You started following testUser2", result);
		assertTrue(currentUser.getFollowing().contains(targetUser));
		assertTrue(targetUser.getFollowers().contains(currentUser));
		
		verify(repo).save(currentUser);
		verify(repo).save(targetUser);
	}
	
	@Test
	void UserNotFoundTest()
	{
		String currentUserName = "testUser1";
		String targetUserName = "testUser2";
		
		when(repo.getUserByUserName(currentUserName)).thenReturn(Optional.empty());
		when(repo.getUserByUserName(targetUserName)).thenReturn(Optional.empty());
		
		String result = service.followTheUser(currentUserName, targetUserName);
		
		assertEquals("User not Found", result);
		verify(repo, never()).save(any(UserProfile.class));
	}
	
	@Test
	void UnfollowTheAnotherUser()
	{
		String currentUserName = "testUser1";
		String targetUserName = "testUser2";
		
		UserProfile currentUser = new UserProfile();
		currentUser.setUserName(currentUserName);
		currentUser.setFollowers(new HashSet<>());
		currentUser.setFollowing(new HashSet<>());
		
		UserProfile targetUser = new UserProfile();
		targetUser.setUserName(targetUserName);
		targetUser.setFollowers(new HashSet<>());
		targetUser.setFollowing(new HashSet<>());
		
		when(repo.getUserByUserName(currentUserName)).thenReturn(Optional.of(currentUser));
		when(repo.getUserByUserName(targetUserName)).thenReturn(Optional.of(targetUser));
		
		String result = service.unFollowTheUser(currentUserName, targetUserName);
		
		assertEquals("You have unfollowed testUser2", result);
		assertFalse(currentUser.getFollowing().contains(targetUser));
		assertFalse(targetUser.getFollowers().contains(currentUser));
		
		verify(repo).save(currentUser);
		verify(repo).save(targetUser);
	}
}
