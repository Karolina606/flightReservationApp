package com.controller;


import com.model.Flight;
import com.modelsRepos.FlightRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
@RequestMapping("/flightRest")
public class FlightController {
    @Autowired
    private FlightRepo flightRepo;

    public FlightController(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    // get all flight
    @GetMapping
    public List<Flight> getAllFlights() {
        return (List<Flight>) flightRepo.findAll();
    }

    // get flights data by id
    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable long id) {
        return flightRepo.findById(id).orElse(null);
    }

    // get occupited seats
    @GetMapping("occupiedSeats/{id}")
    public Integer getOccupiedSeatsId(@PathVariable long id) {
        return flightRepo.getNumberOfOccupiedSeats(id);
    }

    // create flight
    @PostMapping
    public Flight createFlight(@RequestBody Flight flight){
        return flightRepo.save(flight);
    }

    // update flight
    @PutMapping("/{id}")
    public Flight updateFlight(@RequestBody Flight flight,
                                           @PathVariable ("id") long id){
        Flight foundFlight = flightRepo.findById(id).orElseThrow(() -> new NotFoundException("Flight not found, id=" + id));
        foundFlight.setDepartureDate(flight.getDepartureDate());
        foundFlight.setArrivalDate(flight.getArrivalDate());
        foundFlight.setDeparturePlace(flight.getDeparturePlace());
        foundFlight.setArrivalPlace(flight.getArrivalPlace());
        foundFlight.setPrice(flight.getPrice());
        foundFlight.setPlane(flight.getPlane());

        return flightRepo.save(foundFlight);
    }

    // delete flight
    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable ("id") long id){
        Flight foundFlight = flightRepo.findById(id).orElseThrow(() -> new NotFoundException("Flight not found, id=" + id));
        flightRepo.delete(foundFlight);

        return ResponseEntity.ok().build();
    }

//    // ifFlightInDatabase
//    @GetMapping("/ifFlightInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}")
//    public Flight returnIfFlightInDataBase(@PathVariable String country,
//         @PathVariable String city,
//         @PathVariable String postcode,
//         @PathVariable String street,
//         @PathVariable String buildingNr,
//         @PathVariable String apartmentNr
//    ){
//        List<Flight> foundFlights = flightRepo.searchForFlight(
//                country, city, postcode, street, Integer.parseInt(buildingNr), Integer.parseInt(apartmentNr));
////                flight.getCountry(),
////                flight.getCity(),
////                flight.getPostcode(),
////                flight.getStreet(),
////                flight.getBuildingNr(),
////                flight.getApartmentNr());
//
//        if (foundFlights.isEmpty()){
//            return null;
//        }else{
//            return foundFlights.get(0);
//        }
//    }
}
