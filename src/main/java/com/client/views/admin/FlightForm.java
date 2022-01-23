package com.client.views.admin;

import com.client.*;
import com.model.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightForm extends FormLayout {
    TextField flightId = new TextField("Id lotu (do usuwania)");

    DateTimePicker departureDate = new DateTimePicker("Data odlotu");
    DateTimePicker arrivalDate = new DateTimePicker("Data przylotu");

    ComboBox<Airport> departureAirport = new ComboBox<>("Lotnisko odlotu");
    ComboBox<Airport> arrivalAirport = new ComboBox<>("Lotnisko przylotu");
    TextField price = new TextField("Cena");
    ComboBox<Plane> plane = new ComboBox<>("Samolot");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancle = new Button("Cancle");

    FlightView flightViewParent;

    public FlightForm(FlightView flightViewParent){
        this.flightViewParent = flightViewParent;

        // Załadowanie danych do comboboxow
        departureAirport.setItems(AirportRestClient.callGetAllAirportApi());
        departureAirport.setItemLabelGenerator(Airport::getName);

        arrivalAirport.setItems(AirportRestClient.callGetAllAirportApi());
        arrivalAirport.setItemLabelGenerator(Airport::getName);

        plane.setItems(PlaneRestClient.callGetAllPlaneApi());
        plane.setItemLabelGenerator(Plane::toStringShort);

        add(departureDate, arrivalDate, departureAirport, arrivalAirport, price, plane, createButtonLayout());
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancle.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> addFlight());
        delete.addClickListener(event -> deleteFlight());
        return new HorizontalLayout(save, delete, cancle);
    }

    private void deleteFlight() {
        Long FlightId = Long.parseLong(flightId.getValue());
        FlightRestClient.callDeleteFlightApi(FlightId);
        flightViewParent.updateList();
    }

    private void addFlight() {
        BigDecimal newPrice = BigDecimal.valueOf(Float.parseFloat(price.getValue()));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss");
        LocalDateTime newDepartureDate = departureDate.getValue();
        LocalDateTime newArrivalDate = arrivalDate.getValue();

        Plane newPlane = plane.getValue();
        Airport newDepartureAirport = departureAirport.getValue();
        Airport newArrivalAirport = arrivalAirport.getValue();

        Flight flight = new Flight(newDepartureAirport, newArrivalAirport, newDepartureDate, newArrivalDate, newPlane, newPrice);
        FlightRestClient.callCreateFlightApi(flight);

        flightViewParent.updateList();
    }
}
