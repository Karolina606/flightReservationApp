package com.controller;


import com.model.Reservation;
import com.modelsRepos.FlightRepo;
import com.modelsRepos.ReservationRepo;
import com.vaadin.flow.router.NotFoundException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/reservationRest")
public class ReservationController {

    @Autowired
    private ReservationRepo reservationRepo;
    private FlightRepo flightRepo;

    public ReservationController(ReservationRepo reservationRepo, FlightRepo flightRepo) {
        this.reservationRepo = reservationRepo;
        this.flightRepo = flightRepo;
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
        // Osoba poniżej 16 roku życia nie może sama dokonać rezerwacji
        if (Period.between(reservation.getPersonalData().getDateOfBirth(), LocalDate.now()).getYears() < 16 ){
            System.err.println("Osoby poniżej 16 roku zycia nie mogą same dokonywać rezerwacji");
            return null;
        }
        // dodamy rezerwaję tylko kiedy jest jeszcze miejsce
        if (reservation.getFlight().getPlane().getModel().getNumberOfSeats() > flightRepo.getNumberOfOccupiedSeats(reservation.getFlight().getId())){
            return reservationRepo.save(reservation);
        }else{
            // kiedy nie ma odpowiedniej liczby miejsc
            System.err.println("Niestety na ten lot nie ma już miejsca");
            return null;
        }
    }

    // create multiple reservations
    @PostMapping("/createMultipleReservations")
    public Boolean createMultipleReservations(@RequestBody List<Reservation> reservations){
        // czy wsrod rezerwacji jest osoba powyzej 16 roku zycia
        Boolean permissionToMakeReservations = false;
        for(Reservation reservation: reservations){
            if(Period.between(reservation.getPersonalData().getDateOfBirth(), LocalDate.now()).getYears() >= 16 ) {
                System.out.println("Wsrod rezerwacji jest osoba powyzej 16 roku zycia, mozna dokonac rezerwacji");
                permissionToMakeReservations = true;
                break;
            }
        }

        // jesli nie ma osoby powyzej 16 roku zycia
        if(permissionToMakeReservations == false){
            return false;
        }

        // dodamy rezerwaję tylko kiedy jest jeszcze miejsce
        if (reservations.get(0).getFlight().getPlane().getModel().getNumberOfSeats() > reservations.size() + flightRepo.getNumberOfOccupiedSeats(reservations.get(0).getFlight().getId())){
            // jest na tyle wolnych miejsc
            reservationRepo.saveAll(reservations);
            System.out.println("Zapisano kilka rezerwacji");
            return true;
        }else{
            // kiedy nie ma odpowiedniej liczby miejsc
            System.err.println("Niestety na ten lot nie ma już miejsca");
            return false;
        }
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
