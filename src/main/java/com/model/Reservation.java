package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "pesel", referencedColumnName = "pesel", nullable = false)
    private PersonalData personalData;

    public Reservation(Flight flight, PersonalData personalData) {
        this.flight = flight;
        this.personalData = personalData;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }
}
