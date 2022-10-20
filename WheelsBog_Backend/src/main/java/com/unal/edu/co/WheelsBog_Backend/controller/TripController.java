package com.unal.edu.co.WheelsBog_Backend.controller;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.Trip;
import com.unal.edu.co.WheelsBog_Backend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//permit cross origin requests
@CrossOrigin
@RestController
@RequestMapping("/api")
public class TripController {
    // Declares the corresponding service
    @Autowired
    TripService tripService;

    //get http request for all trips
    @GetMapping("/trips")
    public List<Trip> getAllTrips(HttpServletRequest request ) {
        //return the corresponding service logical function
        return tripService.getAllTrips();
    }

    //get location by id
    @GetMapping("/trip/{id}")
    public Trip getLocationById(@PathVariable(value = "id") Long tripId, HttpServletRequest request ) {
        //return the corresponding service logical function
        return tripService.getTripById(tripId);
    }

    //Post http request for trip
    @PostMapping("/trip")
    //request body with object to post
    public Trip createTrip(@RequestBody Trip trip, HttpServletRequest request) {
        //return the corresponding service logical function
        return tripService.createTrip(trip);
    }

    //Delete http request for trip by ID
    @DeleteMapping("delete/trip/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable(value = "id") Long tripId, HttpServletRequest request) {
        //call the corresponding service logical function
        tripService.deleteTrip(tripId);
        //Check deletion
        return ResponseEntity.ok().build();
    }
}
