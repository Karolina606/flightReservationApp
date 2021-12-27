package com.model;

import javax.persistence.*;
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
    private Date inspectionDate;

    public Plane(PlaneModel modelId, String airlines, Date inspectionDate) {
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

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
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
