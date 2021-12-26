package model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonalData personalData;

    @Column(nullable = false)
    private EmployeeEnum empolyeeRole;

    @Column(nullable = false)
    private BigDecimal salary;

    @ManyToMany(mappedBy = "crew", cascade = CascadeType.ALL)
    private List<Flight> flights;

    public Employee(PersonalData pesel, EmployeeEnum empolyeeRole, BigDecimal salary) {
        this.personalData = pesel;
        this.empolyeeRole = empolyeeRole;
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
        return empolyeeRole;
    }

    public void setEmpolyeeRole(EmployeeEnum empolyeeRole) {
        this.empolyeeRole = empolyeeRole;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
