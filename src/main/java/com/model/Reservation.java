package com.model;

import javax.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "pesel", referencedColumnName = "pesel", nullable = false)
    private PersonalData personalData;

    public Reservation(Flight flightId, PersonalData personalData) {
        this.flight = flightId;
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

    public void setFlight(Flight flightId) {
        this.flight = flightId;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }
}
