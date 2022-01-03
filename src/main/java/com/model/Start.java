package com.model;

import com.modelsRepos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class Start {

    private PlaneRepo planeRepo;
    private PlaneModelRepo planeModelRepo;
    private FlightRepo flightRepo;
    private AddressRepo addressRepo;
    private PersonalDataRepo personalDataRepo;
    private EmployeeRepo employeeRepo;

    @Autowired
    public Start(PlaneRepo planeRepo, PlaneModelRepo planeModelRepo, FlightRepo flightRepo, AddressRepo addressRepo, PersonalDataRepo personalDataRepo, EmployeeRepo employeeRepo) {
        this.planeRepo = planeRepo;
        this.planeModelRepo = planeModelRepo;
        this.flightRepo = flightRepo;
        this.addressRepo = addressRepo;
        this.personalDataRepo = personalDataRepo;
        this.employeeRepo = employeeRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExample(){
//        PlaneModel planeModel = new PlaneModel("Boeing", "737", 230, 2, 10, 25940f);
//        Date date = new Date();
//        Plane plane = new Plane(planeModel, "Ryanair", date);
//        planeModelRepo.save(planeModel);
//        planeRepo.save(plane);

//        Address address = new Address("USA", "Atlanta", "43-121", "Streetiat", 3, 12);
//        PersonalData person = new PersonalData(1000022111L, "Steven", "Spielberg", LocalDate.parse("1945-12-03"), 123456789L, address);
//        Employee employee = new Employee(person, EmployeeEnum.PILOT, BigDecimal.valueOf(Double.parseDouble("5000.99")));
//
//        Flight flight = flightRepo.findById(2L).get();
//
//        flight.getCrew().add(employee);
//
//        addressRepo.save(address);
//        personalDataRepo.save(person);
//        employeeRepo.save(employee);
//        flightRepo.save(flight);


        System.out.println("Działam jak coś");
    }
}
