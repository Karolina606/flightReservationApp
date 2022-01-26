package com.client.views.admin;

import com.client.PlaneRestClient;
import com.client.PlaneModelRestClient;
import com.model.Plane;
import com.model.PlaneModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PlaneForm extends FormLayout {
    TextField planeId = new TextField("Id samolotu (do usuwania)");
    TextField airlines = new TextField("Linie lotnicze");

    DatePicker inspectionDate = new DatePicker("Data przeglądu");
    ComboBox<PlaneModel> planeModel = new ComboBox<>("Model samolotu");


    Button save = new Button("Zapisz");
    Button delete = new Button("Usuń");
    Button cancle = new Button("Odrzuć");

    PlaneView planeViewParent;

    public PlaneForm(PlaneView planeViewParent){
        this.planeViewParent = planeViewParent;

        // Załadowanie danych do comboboxow
        planeModel.setItems(PlaneModelRestClient.callGetAllPlaneModelApi());
        planeModel.setItemLabelGenerator(PlaneModel::toStringShort);

        add(planeId, airlines, inspectionDate, planeModel, createButtonLayout());
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancle.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> addPlane());
        delete.addClickListener(event -> deletePlane());
        return new HorizontalLayout(save, delete, cancle);
    }

    private void deletePlane() {
        Long PlaneId = Long.parseLong(planeId.getValue());
        PlaneRestClient.callDeletePlaneApi(PlaneId);
        planeViewParent.updateList();
    }

    private void addPlane() {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newInspectionDate = inspectionDate.getValue();

        String newAirlines = airlines.getValue();
        PlaneModel newPlaneModel = planeModel.getValue();

        Plane newPlane = new Plane(newPlaneModel, newAirlines, newInspectionDate);
        PlaneRestClient.callCreatePlaneApi(newPlane);

        planeViewParent.updateList();
    }
}
