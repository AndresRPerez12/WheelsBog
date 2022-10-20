package com.unal.edu.co.WheelsBog_Backend.controller;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.User;
import com.unal.edu.co.WheelsBog_Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//permit cross origin requests
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    // Declares the corresponding service
    @Autowired
    UserService userService;

    //get http request for all users
    @GetMapping("/users")
    public List<User> getAllUsers(HttpServletRequest request ) {
        //return the corresponding service logical function
        return userService.getAllUsers();
    }

    //Post http request for user
    @PostMapping("/user")
    //request body with object to post
    public User createUser(@RequestBody User user, HttpServletRequest request) {
        //return the corresponding service logical function
        return userService.createUser(user);
    }

    //Delete http request for user by ID
    @DeleteMapping("delete/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long userId, HttpServletRequest request) {
        //call the corresponding service logical function
        userService.deleteUser(userId);
        //Check deletion
        return ResponseEntity.ok().build();
    }
}
