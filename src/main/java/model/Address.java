package model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int buildingNr;

    private int apartmentNr;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Airport airport;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PersonalData> personalData;

    public Address(String country, String city, String postcode, String street, int buildingNr, int apartmentNr) {
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.buildingNr = buildingNr;
        this.apartmentNr = apartmentNr;
    }

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNr() {
        return buildingNr;
    }

    public void setBuildingNr(int buildingNr) {
        this.buildingNr = buildingNr;
    }

    public int getApartmentNr() {
        return apartmentNr;
    }

    public void setApartmentNr(int apartmentNr) {
        this.apartmentNr = apartmentNr;
    }
}
