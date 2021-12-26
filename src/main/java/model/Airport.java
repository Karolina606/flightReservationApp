package model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "departurePlace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flightsDep;

    @OneToMany(mappedBy = "arrivalPlace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flightsArr;

    public Airport(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Airport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address addressId) {
        this.address = addressId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
