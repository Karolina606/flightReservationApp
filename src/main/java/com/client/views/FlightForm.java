package com.client.views;

import com.client.*;
import com.model.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightForm extends FormLayout {
    TextField flightId = new TextField("Id lotu (do usuwania)");
    TextField departureDate = new TextField("Data odlotu");
    TextField arrivalDate = new TextField("Data przylotu");
    TextField departureAirportId = new TextField("Id lotniska odlotu");
    TextField arrivalAirportId = new TextField("Id lotniska przylotu");
    TextField price = new TextField("Cena");
    TextField planeId = new TextField("Id samolotu");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancle = new Button("Cancle");

    FlightView flightViewParent;

    public FlightForm(FlightView flightViewParent){
        this.flightViewParent = flightViewParent;
        add(departureDate, arrivalDate, departureAirportId, arrivalAirportId, price, planeId, createButtonLayout());
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
        Long newDepartureAirportId = Long.parseLong(departureAirportId.getValue());
        Long newArrivalAirportId = Long.parseLong(arrivalAirportId.getValue());
        BigDecimal newPrice = BigDecimal.valueOf(Long.parseLong(price.getValue()));
        Long newPlaneId = Long.parseLong(planeId.getValue());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime newDepartureDate = LocalDateTime.parse(departureDate.getValue(), df);
        LocalDateTime newArrivalDate = LocalDateTime.parse(arrivalDate.getValue(), df);

        Plane plane = PlaneRestClient.callGetPlaneByIdApi(newPlaneId);
        Airport departureAirport = AirportRestClient.callGetAirportByIdApi(newDepartureAirportId);
        Airport arrivalAirport = AirportRestClient.callGetAirportByIdApi(newArrivalAirportId);

//        List<Employee> newCrew = new ArrayList<>();
//        newCrew.add(EmployeeRestClient.callGetEmployeeByIdApi(1L));

        Flight flight = new Flight(departureAirport, arrivalAirport, newDepartureDate, newArrivalDate, plane, newPrice);
        FlightRestClient.callCreateFlightApi(flight);

        flightViewParent.updateList();
    }
}
