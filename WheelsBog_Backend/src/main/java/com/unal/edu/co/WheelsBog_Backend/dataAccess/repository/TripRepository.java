package com.unal.edu.co.WheelsBog_Backend.dataAccess.repository;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TripRepository inherits from JpaRepository for communication with database
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
