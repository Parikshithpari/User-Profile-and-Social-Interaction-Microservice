package com.example.demo.clientConfig;

import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.dto.UsersDTO;

@FeignClient(name = "UserRegistrationUsingEmail", url = "http://localhost:8080")
public interface AuthClient 
{
	@GetMapping("/getUserByEmail/{email}")
	public Optional<UsersDTO> getUserByEmailId(@PathVariable String email);

}
