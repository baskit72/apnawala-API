package com.APIwebsitebuilder.websitebuilder.repository;

import com.APIwebsitebuilder.websitebuilder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
