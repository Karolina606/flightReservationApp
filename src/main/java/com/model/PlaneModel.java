package com.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class PlaneModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private int numberOfSeats;

    @Column(nullable = false)
    private int numberOfPilots;

    @Column(nullable = false)
    private int numberOfFlightAttendants;

    @Column(nullable = false)
    private float tankCapacity;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Plane> planes;

    public PlaneModel(String brand, String modelName, int numberOfSeats, int numberOfPilots, int numberOfFlightAttendants, float tankCapacity) {
        this.brand = brand;
        this.modelName = modelName;
        this.numberOfSeats = numberOfSeats;
        this.numberOfPilots = numberOfPilots;
        this.numberOfFlightAttendants = numberOfFlightAttendants;
        this.tankCapacity = tankCapacity;
    }

    public PlaneModel() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfPilots() {
        return numberOfPilots;
    }

    public void setNumberOfPilots(int numberOfPilots) {
        this.numberOfPilots = numberOfPilots;
    }

    public int getNumberOfFlightAttendants() {
        return numberOfFlightAttendants;
    }

    public void setNumberOfFlightAttendants(int numberOfFlightAttendants) {
        this.numberOfFlightAttendants = numberOfFlightAttendants;
    }

    public float getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(float tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String toStringShort(){
        return  id + " " + getBrand() + " " + getModelName();
    }

    @Override
    public String toString() {
        return "PlaneModel{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", modelName='" + modelName + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", numberOfPilots=" + numberOfPilots +
                ", numberOfFlightAttendants=" + numberOfFlightAttendants +
                ", tankCapacity=" + tankCapacity +
                '}';
    }
}
