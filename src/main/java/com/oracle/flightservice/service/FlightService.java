package com.oracle.flightservice.service;

import com.oracle.flightservice.models.Flight;
import com.oracle.flightservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    public Flight addFlight(Flight flight) {
        // what to do if if all data same with diff id? prepare hash and check hash
        System.out.println(flight.getFlightId());
        return flightRepository.save(flight);

    }

    public Flight updateFlight(Integer flightId, Flight flight) {
        if (flightRepository.existsById(flightId)){
            return flightRepository.save(flight);
        }
        return null;
    }

    public Optional<Flight> getFlightById(Integer flightId) {
        return flightRepository.findById(flightId);
    }

    public void deleteFlight(Integer flightId) {
        flightRepository.deleteById(flightId);
    }

    public Flight getFlightWithSrcAndDest(String src, String dest) {
        return flightRepository.findBySourceAndDest(src, dest);
    }

    public List<Integer> getUsersOfFlight(Integer flightId) {
        List<Integer> users= Arrays.asList(1,2,3);
        return users;
    }
    public boolean checkIfSeatsAvailable(Integer flightId){
        Flight flight= getFlightById(flightId).orElseThrow();
        Integer seats= flight.getAvailableSeats();
        if(seats-1>=0){
            flight.setAvailableSeats(seats-1);
            Flight updatedFlight = flightRepository.save(flight);
            return true;
        }
        return false;
    }
}
