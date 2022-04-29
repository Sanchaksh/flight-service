package com.oracle.flightservice.repository;

import com.oracle.flightservice.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer>{

    Flight findBySourceAndDest(String src, String dest);
}
