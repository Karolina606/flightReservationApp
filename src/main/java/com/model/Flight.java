package com.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Airport departurePlace;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Airport arrivalPlace;

    @Column(nullable = false)
    private LocalDateTime departureDate;

    @Column(nullable = false)
    private LocalDateTime arrivalDate;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Plane plane;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Reservation> reservations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "flight_crew",
            joinColumns = {
                    @JoinColumn(name = "flights_id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "crew_id", nullable = false, updatable = false)
            }
    )
    //@JoinColumn(referencedColumnName = "id")
    private List<Employee> crew;

    public Flight() {
    }

    public Flight(Airport departurePlace, Airport arrivalPlace, LocalDateTime departureDate, LocalDateTime arrivalDate, Plane plane, BigDecimal price) {
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.plane = plane;
        this.price = price;
    }

    public Airport getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(Airport departurePlace) {
        this.departurePlace = departurePlace;
    }

    public Airport getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(Airport arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public void setReservations(List<Reservation> reservations) {
//        this.reservations = reservations;
//    }

    public List<Employee> getCrew() {
        return crew;
    }

    public void setCrew(List<Employee> crew) {
        this.crew = crew;
    }
}
