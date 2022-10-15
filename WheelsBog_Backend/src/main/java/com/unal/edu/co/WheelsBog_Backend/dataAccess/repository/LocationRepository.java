package com.unal.edu.co.WheelsBog_Backend.dataAccess.repository;

import com.unal.edu.co.WheelsBog_Backend.dataAccess.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//LocationRepository inherits from JpaRepository for communication with database
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
