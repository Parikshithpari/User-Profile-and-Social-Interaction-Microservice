package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userName")

public class UserProfile 
{
	@Id
	private UUID userId;
	
	@Column(unique = true, nullable = false)
	private String userName;
	
	private String bio;
	
	@ManyToMany
	@JoinTable(name = "user_followers",
				joinColumns = @JoinColumn(name ="user_id"),
				inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private Set<UserProfile> followers = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "user_following",
				joinColumns = @JoinColumn(name ="user_id"),
				inverseJoinColumns = @JoinColumn(name = "following_id"))
	private Set<UserProfile> following = new HashSet<>();

	public UserProfile() 
	{
		super();
	}

	public UserProfile(UUID userId, String userName, String bio, Set<UserProfile> followers,
			Set<UserProfile> following) 
	{
		super();
		this.userId = userId;
		this.userName = userName;
		this.bio = bio;
		this.followers = followers;
		this.following = following;
	}

	public UUID getUserId() 
	{
		return userId;
	}

	public void setUserId(UUID userId) 
	{
		this.userId = userId;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getBio() 
	{
		return bio;
	}

	public void setBio(String bio) 
	{
		this.bio = bio;
	}

	public void setFollowers(Set<UserProfile> followers) 
	{
		this.followers = followers;
	}

	public Set<UserProfile> getFollowing() 
	{
		return following;
	}

	public void setFollowing(Set<UserProfile> following) 
	{
		this.following = following;
	}
	
	public Set<UserProfile> getFollowers() 
	{
		return followers;
	}

	public UserProfile orElse(Object object) 
	{
		return null;
	}
}
