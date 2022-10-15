package com.unal.edu.co.WheelsBog_Backend.dataAccess.controller;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.Location;
import com.unal.edu.co.WheelsBog_Backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//permit cross origin requests
@CrossOrigin
@RestController
@RequestMapping("/api")
public class LocationController {
    // Declares the corresponding service
    @Autowired
    LocationService locationService;

    //get http request for all locations
    @GetMapping("/locations")
    public List<Location> getAllLocations(HttpServletRequest request ) {
        //return the corresponding service logical function
        return locationService.getAllLocations();
    }

    //get location by id
    @GetMapping("/location/{id}")
    public Location getLocationById(@PathVariable(value = "id") Long locationId, HttpServletRequest request ) {
        //return the corresponding service logical function
        return locationService.getLocationById(locationId);
    }

    //Post http request for location
    @PostMapping("/location")
    //request body with object to post
    public Location createLocation(@RequestBody Location location, HttpServletRequest request) {
        //return the corresponding service logical function
        return locationService.createLocation(location);
    }

    //Delete http request for location by ID
    @DeleteMapping("delete/location/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable(value = "id") Long locationId, HttpServletRequest request) {
        //call the corresponding service logical function
        locationService.deleteLocation(locationId);
        //Check deletion
        return ResponseEntity.ok().build();
    }
}
