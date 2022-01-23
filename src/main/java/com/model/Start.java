package com.model;

import com.controller.ReservationController;
import com.modelsRepos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cglib.core.Local;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Start {

    private PlaneRepo planeRepo;
    private PlaneModelRepo planeModelRepo;
    private FlightRepo flightRepo;
    private AddressRepo addressRepo;
    private PersonalDataRepo personalDataRepo;
    private EmployeeRepo employeeRepo;
    private AirportRepo airportRepo;
    private ReservationRepo reservationRepo;

    @Autowired
    public Start(PlaneRepo planeRepo, PlaneModelRepo planeModelRepo, FlightRepo flightRepo, AddressRepo addressRepo,
                 PersonalDataRepo personalDataRepo, EmployeeRepo employeeRepo, AirportRepo airportRepo, ReservationRepo reservationRepo) {
        this.planeRepo = planeRepo;
        this.planeModelRepo = planeModelRepo;
        this.flightRepo = flightRepo;
        this.addressRepo = addressRepo;
        this.personalDataRepo = personalDataRepo;
        this.employeeRepo = employeeRepo;
        this.airportRepo = airportRepo;
        this.reservationRepo = reservationRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExample(){

//        Dodanie nowego samolotu ###########################################################################
//        PlaneModel planeModel = new PlaneModel("Boeing", "737", 230, 2, 10, 25940f);
//        Date date = new Date();
//        Plane plane = new Plane(planeModel, "Ryanair", date);
//        planeModelRepo.save(planeModel);
//        planeRepo.save(plane);

//        Dodanie nowego pracownika ###########################################################################
//        Address address = new Address("USA", "Atlanta", "43-121", "Streetiat", 3, 12);
//        PersonalData person = new PersonalData(1000022111L, "Steven", "Spielberg", LocalDate.parse("1945-12-03"), 123456789L, address);
//        Employee employee = new Employee(person, EmployeeEnum.PILOT, BigDecimal.valueOf(Double.parseDouble("5000.99")));
//
//        Dodanie pracownika do lotu ###########################################################################
//        Flight flight = flightRepo.findById(2L).get();
//        Employee employee = employeeRepo.findById(4L).get();
//
//        flight.getCrew().add(employee);
//
////        addressRepo.save(address);
////        personalDataRepo.save(person);
//        //employeeRepo.save(employee);
//        flightRepo.save(flight);


        // Lot awionetką ######################################################################################
//        ReservationController resController = new ReservationController(reservationRepo, flightRepo);
//
//        Airport departurePlace = airportRepo.findById(1L).get();
//        Airport arrivalPlace = airportRepo.findById(2L).get();
//        Plane plane = planeRepo.findById(21L).get();
//        Flight flight = new Flight(departurePlace, arrivalPlace, LocalDateTime.now().plusHours(2L), LocalDateTime.now().plusHours(3L),
//                plane, BigDecimal.valueOf(600.00));
//        flightRepo.save(flight);
//
//        PersonalData person1 = personalDataRepo.findById(10000000000L).get();
//        Reservation reservation1 = new Reservation(flight, person1);
//        resController.createReservation(reservation1);
//
//        // próba dodania do lotu drugiej osoby
//        PersonalData person2 = personalDataRepo.findById(10000000004L).get();
//        Reservation reservation2 = new Reservation(flight, person2);
//        resController.createReservation(reservation2);
//
//        // próba dodania do lotu trzeciej osoby
//        PersonalData person3 = personalDataRepo.findById(10000000006L).get();
//        Reservation reservation3 = new Reservation(flight, person3);
//        resController.createReservation(reservation3);


        System.out.println("Działam jak coś");
    }
}
