package com.unal.edu.co.WheelsBog_Backend.service;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.exception.ResourceNotFoundException;
import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.Trip;
import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.User;
import com.unal.edu.co.WheelsBog_Backend.dataAccess.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Business logic layer for Trip, receives calls from TripController and calls TripRepository
@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserService userService;

    public List<Trip> getAllTrips() { // returns a list with all Trips in the database
        try{
            return tripRepository.findAll();
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public Trip getTripById(Long id ) { // returns the Trip with the requested ID or an exception if it does not exist
        try{
            return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip", "id", id));
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public Trip createTrip(Trip trip) { // creates a new Trip in the database
        try{
            return tripRepository.save(trip);
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public void deleteTrip( Long id ) { // deletes the Trip with the requested ID or an exception if it does not exist
        try{
            Trip trip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip", "id", id));
            tripRepository.delete(trip);
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }
    }

    public Trip addPassenger( Long tripId , Long userId ){
        User user = userService.getUserById(userId);
        Trip trip = getTripById(tripId);
        try{
            trip.getPassengers().add(user);
            trip = tripRepository.save(trip);
            return trip;
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }
    }
}
