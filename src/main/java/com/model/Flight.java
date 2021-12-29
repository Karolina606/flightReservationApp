package com.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "id", nullable = false)
    private Plane plane;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Reservation> reservations;

    @ManyToMany
    @JoinColumn(name = "crew", referencedColumnName = "id")
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

    private BigDecimal price;

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

    public void setPlane(Plane planeId) {
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
}
