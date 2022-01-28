package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PlaneModel model;

    @Column(nullable = false)
    private String airlines;

    @Column(nullable = false)
    private LocalDate inspectionDate;

    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Flight> flightList;

    public Plane(PlaneModel modelId, String airlines, LocalDate inspectionDate) {
        this.model = modelId;
        this.airlines = airlines;
        this.inspectionDate = inspectionDate;
    }

    public Plane() {
    }

    public PlaneModel getModel() {
        return model;
    }

    public void setModel(PlaneModel modelId) {
        this.model = modelId;
    }

    public String getAirlines() {
        return airlines;
    }

    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

//    public List<Flight> getFlightList() {
//        return flightList;
//    }
//
//    public void setFlightList(List<Flight> flightList) {
//        this.flightList = flightList;
//    }

    public String toStringShort(){
        return  id + " " + model.getBrand() + " " + model.getModelName();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", modelId=" + model +
                ", airlines='" + airlines + '\'' +
                '}';
    }
}
