package com.controller;


import com.model.Employee;
import com.model.PersonalData;
import com.modelsRepos.EmployeeRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeRest")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;

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
