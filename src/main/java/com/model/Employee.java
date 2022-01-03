package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pesel", referencedColumnName = "pesel", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonalData personalData;

    @Column(nullable = false)
    private EmployeeEnum employeeRole;

    @Column(nullable = false)
    private BigDecimal salary;

    @ManyToMany(mappedBy = "crew", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    List<Flight> flights;

    public Employee(PersonalData pesel, EmployeeEnum empolyeeRole, BigDecimal salary) {
        this.personalData = pesel;
        this.employeeRole = empolyeeRole;
        this.salary = salary;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData pesel) {
        this.personalData = pesel;
    }

    public EmployeeEnum getEmpolyeeRole() {
        return employeeRole;
    }

    public void setEmpolyeeRole(EmployeeEnum empolyeeRole) {
        this.employeeRole = empolyeeRole;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
