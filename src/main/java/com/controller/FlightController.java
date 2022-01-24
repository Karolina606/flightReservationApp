package com.controller;


import com.model.Flight;
import com.model.Plane;
import com.modelsRepos.FlightRepo;
import com.modelsRepos.PlaneRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@Component
@RequestMapping("/flightRest")
public class FlightController {
    @Autowired
    private FlightRepo flightRepo;
    private PlaneRepo planeRepo;

    public FlightController(FlightRepo flightRepo, PlaneRepo planeRepo) {
        this.flightRepo = flightRepo;
        this.planeRepo = planeRepo;
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
        // Sprawdzenie samolotu, czy przegląd jest ważny
        Plane plane;
        if(planeRepo.findById(flight.getPlane().getId()).isPresent()){
            plane = planeRepo.findById(flight.getPlane().getId()).get();
            LocalDateTime arrivalDate = flight.getArrivalDate();
            LocalDateTime inspectionDate = LocalDateTime.of(plane.getInspectionDate(), LocalTime.of(0,0,0));

            if(arrivalDate.isBefore(inspectionDate)){
                System.out.println("Samolot bedzie mial wazny przeglad podczas lotu, mozna go dodac.");
                return flightRepo.save(flight);
            }
            else{
                System.err.println("Niestety data waznosci przegladu samolotu nie pozwala na dodanie go do lotu.");
                return null;
            }
        }else{
            System.err.println("Nie ma samolotu o tym id, nie mozesz go przypisać do lotu, id= " + flight.getPlane().getId());
            return null;
        }
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

        // sprawdzenie czy samolot moze byc przypisany do lotu
        LocalDateTime arrivalDate = flight.getArrivalDate();
        LocalDateTime inspectionDate = LocalDateTime.of(flight.getPlane().getInspectionDate(), LocalTime.of(0,0,0));

        if(arrivalDate.isBefore(inspectionDate)){
            System.out.println("Samolot bedzie mial wazny przeglad podczas lotu, mozna go dodac.");
            foundFlight.setPlane(flight.getPlane());
            return flightRepo.save(foundFlight);
        }
        else{
            System.err.println("Niestety data waznosci przegladu samolotu nie pozwala na dodanie go do lotu.");
            return null;
        }
        //return flightRepo.save(foundFlight);
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
