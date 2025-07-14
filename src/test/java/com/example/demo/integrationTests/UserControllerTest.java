package com.example.demo.integrationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.dto.UsersDTO;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest 
{
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository repo;
	
	@MockBean
	private UserService service;
	
	@BeforeEach
	void setUp()
	{
		repo.deleteAll();
	}
	
	@Test
	void GetTheUserByTheEmailTest() throws Exception
	{
		UUID userId = UUID.randomUUID();
		String email = "testMail@mail.com";
		
		UsersDTO mockUser = new UsersDTO();
		mockUser.setEmail(email);
		mockUser.setUserId(userId);
		mockUser.setVerified(true);
		
		Mockito.when(service.getUserByEmail(email)).thenReturn(Optional.of(mockUser));
		
		mockMvc.perform(get("/getUserByEmail/testMail@mail.com"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userId").value(userId.toString()))
		.andExpect(jsonPath("$.email").value("testMail@mail.com"))
		.andExpect(jsonPath("$.verified").value(true));
	}
	
	@Test
	void CreateTheUserProfileEndPointTest() throws Exception 
	{
		UUID userId = UUID.randomUUID();
		
		mockMvc.perform(post("/create").param("email", "testMail@mail.com")
				.param("userId", userId.toString())
				.param("userName", "testUser1")
				.param("bio", "hello all"))
		.andExpect(status().isOk());
	}
	
	@Test
	void FollowTheUserEndPointTest() throws Exception
	{
		String currentUserName = "testUser1";
		String targetUserName = "testUser2";
		
		Mockito.when(service.followTheUser(currentUserName, targetUserName))
		.thenReturn("You started following "+targetUserName);
		
		mockMvc.perform(post("/follow/testUser1/testUser2"))
		.andExpect(status().isOk());
 	}
	
	@Test
	void UnFollowTheUserEndPointTest() throws Exception
	{
		String currentUserName = "testUser1";
		String targetUserName = "testUser2";
		
		Mockito.when(service.unFollowTheUser(currentUserName, targetUserName))
		.thenReturn("You have unfollowed "+targetUserName);
		
		mockMvc.perform(post("/unfollow/testUser1/testUser2"))
		.andExpect(status().isOk());
	}
	
	@Test
	void GetTheRegsiteredUser() throws Exception
	{
		UUID userId = UUID.randomUUID();
		String userName = "testUser1";
		String bio = "testBio";
		
		UserProfile mockUser = new UserProfile();
		mockUser.setUserId(userId);
		mockUser.setUserName(userName);
		mockUser.setBio(bio);
		
		Mockito.when(service.getUsersByUserName(userName)).thenReturn(Optional.of(mockUser));
		
		mockMvc.perform(get("/getUser/testUser1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userId").value(userId.toString()))
		.andExpect(jsonPath("$.userName").value("testUser1"))
		.andExpect(jsonPath("$.bio").value("testBio"));
	}
}
