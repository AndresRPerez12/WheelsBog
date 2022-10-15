package com.unal.edu.co.WheelsBog_Backend.service;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.exception.ResourceNotFoundException;
import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.Location;
import com.unal.edu.co.WheelsBog_Backend.dataAccess.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Business logic layer for Location, receives calls from LocationController and calls LocationRepository
@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public List<Location> getAllLocations() { // returns a list with all Locations in the database
        try{
            return locationRepository.findAll();
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public Location getLocationById(Long id ) { // returns the Location with the requested ID or an exception if it does not exist
        try{
            return locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public Location createLocation(Location location) { // creates a new Location in the database
        try{
            return locationRepository.save(location);
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }

    }

    public void deleteLocation( Long id ) { // deletes the Location with the requested ID or an exception if it does not exist
        try{
            Location location = locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
            locationRepository.delete(location);
        }catch( Exception e ){
            System.out.println("Exception:" + e);
            throw e;
        }
    }
}
