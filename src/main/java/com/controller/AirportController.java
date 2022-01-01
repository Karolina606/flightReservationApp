package com.controller;


import com.model.Airport;
import com.modelsRepos.AirportRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airportRest")
public class AirportController {

    @Autowired
    private AirportRepo airportRepo;

    public AirportController(AirportRepo airportRepo) {
        this.airportRepo = airportRepo;
    }

    // get all airport
    @GetMapping
    public List<Airport> getAllAirportes() {
        return (List<Airport>) airportRepo.findAll();
    }

    // get airports data by id
    @GetMapping("/{id}")
    public Airport getAirportById(@PathVariable long id) {
        return airportRepo.findById(id).orElse(null);
    }

    // create airport
    @PostMapping
    public Airport createAirport(@RequestBody Airport airport){
        return airportRepo.save(airport);
    }

    // update airport
    @PutMapping("/{id}")
    public Airport updateAirport(@RequestBody Airport airport,
                                           @PathVariable ("id") long id){
        Airport foundAirport = airportRepo.findById(id).orElseThrow(() -> new NotFoundException("Airport not found, id=" + id));
        foundAirport.setName(airport.getName());
        foundAirport.setAddress(airport.getAddress());

        return airportRepo.save(foundAirport);
    }

    // delete airport
    @DeleteMapping("/{id}")
    public ResponseEntity<Airport> deleteAirport(@PathVariable ("id") long id){
        Airport foundAirport = airportRepo.findById(id).orElseThrow(() -> new NotFoundException("Airport not found, id=" + id));
        airportRepo.delete(foundAirport);

        return ResponseEntity.ok().build();
    }

//    // ifAirportInDatabase
//    @GetMapping("/ifAirportInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}")
//    public Airport returnIfAirportInDataBase(@PathVariable String country,
//         @PathVariable String city,
//         @PathVariable String postcode,
//         @PathVariable String street,
//         @PathVariable String buildingNr,
//         @PathVariable String apartmentNr
//    ){
//        List<Airport> foundAirports = airportRepo.searchForAirport(
//                country, city, postcode, street, Integer.parseInt(buildingNr), Integer.parseInt(apartmentNr));
////                airport.getCountry(),
////                airport.getCity(),
////                airport.getPostcode(),
////                airport.getStreet(),
////                airport.getBuildingNr(),
////                airport.getApartmentNr());
//
//        if (foundAirports.isEmpty()){
//            return null;
//        }else{
//            return foundAirports.get(0);
//        }
//    }
}
