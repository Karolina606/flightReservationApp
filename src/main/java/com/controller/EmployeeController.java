package com.controller;


import com.model.Employee;
import com.model.EmployeeEnum;
import com.model.Flight;
import com.model.PersonalData;
import com.modelsRepos.EmployeeRepo;
import com.modelsRepos.FlightRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/employeeRest")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;
    private FlightRepo flightRepo;

    public EmployeeController(EmployeeRepo employeeRepo, FlightRepo flightRepo) {
        this.employeeRepo = employeeRepo;
        this.flightRepo = flightRepo;
    }

    // get all employee
    @GetMapping
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepo.findAll();
    }

    // get employees data by id
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    // add employee to flight
    @PostMapping("/addEmployeeToFlightCrew/{flightId}")
    public Employee addEmployeeToFlightCrew(@RequestBody Employee employee, @PathVariable Long flightId){
        // doda członka załogi do lotu jeśli:
        // 1. Nie wylatał on jeszcze w tym miesiacu swoich godzin (100 - stewardessa, 80 - pilot)
        // 2. Są wolne miejsca dla to "stanowisko dla tego lotu"

        Flight flight;
        if (flightRepo.findById(flightId).isPresent()){
            flight = flightRepo.findById(flightId).get();
        }else{
            System.err.println("Nie znaleziono lotu, id= " + flightId);
            return null;
        }

        long flightDurationInHours = Math.round(ChronoUnit.MINUTES.between(flight.getDepartureDate(), flight.getArrivalDate()) / 60.0 );
        long workedHoursInMonth = employeeRepo.getWorkedHoursInMonth(employee.getId(), flight.getDepartureDate());
        int numberOfPilotsForFlight = employeeRepo.howManyPilotsAlreadyInFlight(flightId);
        int numberOfStewardessForFlight = employeeRepo.howManyStewardessAlreadyInFlight(flightId);

        if (employee.getEmpolyeeRole() == EmployeeEnum.PILOT && workedHoursInMonth + flightDurationInHours <= 80){
            // sprawdzamy czy nie jest już odpowiednia liczba pilotow przypisana do lotu
            if (numberOfPilotsForFlight < flight.getPlane().getModel().getNumberOfPilots()){
                System.out.println("Lot nie ma jeszcze wszystkich pilotow, mozna przypisac.");
                flight.getCrew().add(employee);
                return employeeRepo.save(employee);
            }else{
                System.err.println("Lot ma juz wyszytkich pilotow, nie mozna przypisac tego pilota do tego lotu.");
            }
        }
        else if (employee.getEmpolyeeRole() == EmployeeEnum.STEWARDESS && workedHoursInMonth + flightDurationInHours <= 100){
            // sprawdzamy czy nie jest już odpowiednia liczba stewardess przypisana do lotu
            if (numberOfStewardessForFlight < flight.getPlane().getModel().getNumberOfFlightAttendants()){
                System.out.println("Lot nie ma jeszcze wszystkie stewardess, mozna przypisac.");
                flight.getCrew().add(employee);
                return employeeRepo.save(employee);
            }else{
                System.err.println("Lot ma juz wyszytkie stewardess, nie mozna przypisac tego pilota do tego lotu.");
            }
        }
        else{
            System.err.println("Pracownik nie moze poleciec az tyle godzin we wskazanym miesiacu");
        }
        return null;
    }

    // create employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepo.save(employee);
    }


    // update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee employee,
                                           @PathVariable ("id") long id){
        Employee foundEmployee = employeeRepo.findById(id).orElseThrow(() -> new NotFoundException("Employee not found, id=" + id));
        foundEmployee.setEmpolyeeRole(employee.getEmpolyeeRole());
        foundEmployee.setPersonalData(employee.getPersonalData());
        foundEmployee.setSalary(employee.getSalary());

        return employeeRepo.save(foundEmployee);
    }

    // delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable ("id") long id){
        Employee foundEmployee = employeeRepo.findById(id).orElseThrow(() -> new NotFoundException("Employee not found, id=" + id));
        employeeRepo.delete(foundEmployee);

        return ResponseEntity.ok().build();
    }
}
