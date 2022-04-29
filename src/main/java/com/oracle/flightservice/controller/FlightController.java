package com.oracle.flightservice.controller;

import com.oracle.flightservice.models.Booking;
import com.oracle.flightservice.models.Flight;
import com.oracle.flightservice.models.User;
import com.oracle.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/allFlights")
    public List<Flight> getFlights(){
        return flightService.getFlights();
    }

    @GetMapping("/{flightId}")
    public Optional<Flight> getFlightById(@PathVariable("flightId") Integer flightId ){
        return flightService.getFlightById(flightId);
    }

    @PostMapping("/addFlight")
    public Flight addFlight(@RequestBody Flight flight){

        return flightService.addFlight(flight);
    }

    @PutMapping("/updateFlight/{flightId}")
    public Flight updateFlight(@RequestBody Flight flight, @PathVariable("flightId") Integer flightId){
        return flightService.updateFlight(flightId, flight);
    }

    @DeleteMapping("/deleteFlight/{flightId}")
    public void deleteFlight(@PathVariable("flightId") Integer flightId ){

        flightService.deleteFlight(flightId);
    }

    @GetMapping("/{src}/{dest}")
    public Integer getFlightWithSrcAndDest(@PathVariable("src") String src, @PathVariable("dest") String dest){
        Flight flight=flightService.getFlightWithSrcAndDest(src, dest);
        return flight.getFlightId();
    }

    @GetMapping("/users/{flightId}")
    public List<User> getUsersOfFlight(@PathVariable("flightId") Integer flightId){
        ResponseEntity<Booking[]> response = restTemplate.getForEntity("http://localhost:8082/reservation/"+flightId,Booking[].class);
        List<Booking> bookings= List.of(response.getBody());

        List<Integer> passengers= new ArrayList<>();
        for (Booking booking: bookings){
            passengers.add(booking.getuserId());
        }

        List<User> passengerDetails= new ArrayList<>();

        for(Integer passengerId: passengers){
            User user= restTemplate.getForObject("http://localhost:8080/user/find/"+passengerId, User.class);
            passengerDetails.add(user);
        }

//        return passengers;
        return passengerDetails;

    }

    @GetMapping("/checkIfSeatsAvailable/{flightId}")
    public boolean checkIfSeatsAvailable(@PathVariable Integer flightId){
        return flightService.checkIfSeatsAvailable(flightId);
    }

}

