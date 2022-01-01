package com.client.views;

//import com.controller.FlightController;
import com.client.FlightRestClient;
import com.model.Flight;
import com.model.Flight;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.Clock;
import java.time.LocalDateTime;

@PageTitle("Loty")
@Route(value = "flightRestApi")
public class FlightView extends VerticalLayout {

    Grid<Flight> grid = new Grid<>(Flight.class);
    TextField filterText = new TextField();
    FlightForm form;

    public FlightView(){
        add(new H2("Loty"));

        setSizeFull();

        configureGrid();
        configureForm();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
    }

    public void updateList() {
        grid.setItems(FlightRestClient.callGetAllFlightApi());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new FlightForm(this);
        form.setWidth("25em");
        form.setVisible(false);
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Lot...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addFlightBtn = new Button("Manage flights");
        addFlightBtn.addClickListener(event -> showHideFlightManager());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addFlightBtn);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void showHideFlightManager() {
        form.setVisible(!form.isVisible());
    }


    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("id", "departureDate", "arrivalDate", "price");
        grid.addColumn(flight -> flight.getDeparturePlace().getName()).setHeader("DepartureAirport");
        grid.addColumn(flight -> flight.getDeparturePlace().getAddress().getCity() == null ?
                "Empty" :
                flight.getDeparturePlace().getAddress().getCity()
        ).setHeader("DepartureCity");
        grid.addColumn(flight -> flight.getArrivalPlace().getName()).setHeader("ArrivalAirport");
        grid.addColumn(flight -> flight.getArrivalPlace().getAddress().getCity()).setHeader("ArrivalCity");
        grid.addColumn(flight -> (flight.getPlane().getModel().getBrand() + " " + flight.getPlane().getModel().getModelName())).setHeader("PlaneModel");
        grid.addColumn(flight -> flight.getPlane().getAirlines()).setHeader("Airlines");
        grid.addColumn(flight -> flight.getPlane().getModel().getNumberOfSeats() - FlightRestClient.callGetOccupiedSeats(flight.getId())).setHeader("FreeSeats");
        grid.addColumn(flight -> FlightRestClient.callGetOccupiedSeats(flight.getId())).setHeader("OccupiedSeats");
        grid.addColumn(flight -> flight.getPlane().getAirlines()).setHeader("Airlines");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
