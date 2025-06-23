package com.example.demo.dto;

import java.util.UUID;

public class UsersDTO 
{
	private UUID userId;
	private String email;
	private boolean verified;
	
	public UsersDTO(UUID userId, String email, boolean verified) 
	{
		super();
		this.userId = userId;
		this.email = email;
		this.verified = verified;
	}

	public UsersDTO() 
	{
		super();
	}

	public UUID getUserId()
	{
		return userId;
	}

	public void setUserId(UUID userId) 
	{
		this.userId = userId;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public boolean isVerified() 
	{
		return verified;
	}

	public void setVerified(boolean verified) 
	{
		this.verified = verified;
	}
}
