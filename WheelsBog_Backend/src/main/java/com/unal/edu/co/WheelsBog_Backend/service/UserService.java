package com.unal.edu.co.WheelsBog_Backend.service;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.exception.ResourceNotFoundException;
import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.User;
import com.unal.edu.co.WheelsBog_Backend.dataAccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Business logic layer for User, receives calls from UserController and calls UserRepository
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() { // returns a list with all Users in the database
        try{
            return userRepository.findAll();
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public User getUserById( Long id ) { // returns the User with the requested ID or an exception if it does not exist
        try{
            return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public User createUser(User user) { // creates a new User in the database
        try{
            return userRepository.save(user);
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public void deleteUser( Long id ) { // deletes the User with the requested ID or an exception if it does not exist
        try{
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
            userRepository.delete(user);
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }
    }

    public User findByEmail( String email ){
        return userRepository.findByEmail( email );
    }
}
