package com.unal.edu.co.WheelsBog_Backend.dataAccess.repository;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//UserRepository inherits from JpaRepository for communication with database
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail( String email ); // Find user by email for login
}
