package com.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    private PlaneModel model;

    @Column(nullable = false)
    private String airlines;

    @Column(nullable = false)
    private LocalDate inspectionDate;

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

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", modelId=" + model +
                ", airlines='" + airlines + '\'' +
                '}';
    }
}