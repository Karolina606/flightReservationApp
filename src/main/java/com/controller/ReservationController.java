package com.controller;


import com.model.Reservation;
import com.modelsRepos.ReservationRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservationRest")
public class ReservationController {

    @Autowired
    private ReservationRepo reservationRepo;

    public ReservationController(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }


    // get all reservation
    @GetMapping
    public List<Reservation> getAllReservations() {
        return (List<Reservation>) reservationRepo.findAll();
    }

    // get reservations data by id
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable long id) {
        return reservationRepo.findById(id).orElse(null);
    }

    // create reservation
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation){
        return reservationRepo.save(reservation);
    }

    // update reservation
    @PutMapping("/{id}")
    public Reservation updateReservation(@RequestBody Reservation reservation,
                                           @PathVariable ("id") long id){
        Reservation foundReservation = reservationRepo.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found, id=" + id));
        foundReservation.setFlight(reservation.getFlight());
        foundReservation.setPersonalData(reservation.getPersonalData());

        return reservationRepo.save(foundReservation);
    }

    // delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable ("id") long id){
        Reservation foundReservation = reservationRepo.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found, id=" + id));
        reservationRepo.delete(foundReservation);

        return ResponseEntity.ok().build();
    }

//    // ifReservationInDatabase
//    @GetMapping("/ifReservationInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}")
//    public Reservation returnIfReservationInDataBase(@PathVariable String country,
//         @PathVariable String city,
//         @PathVariable String postcode,
//         @PathVariable String street,
//         @PathVariable String buildingNr,
//         @PathVariable String apartmentNr
//    ){
//        List<Reservation> foundReservationes = reservationRepo.searchForReservation(
//                country, city, postcode, street, Integer.parseInt(buildingNr), Integer.parseInt(apartmentNr));
////                reservation.getCountry(),
////                reservation.getCity(),
////                reservation.getPostcode(),
////                reservation.getStreet(),
////                reservation.getBuildingNr(),
////                reservation.getApartmentNr());
//
//        if (foundReservationes.isEmpty()){
//            return null;
//        }else{
//            return foundReservationes.get(0);
//        }
//    }
}
