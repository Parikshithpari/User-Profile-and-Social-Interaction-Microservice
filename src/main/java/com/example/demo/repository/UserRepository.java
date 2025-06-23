package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.UserProfile;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, UUID>
{

	Optional<UserProfile> getUserByUserName(String userName);
	
}
