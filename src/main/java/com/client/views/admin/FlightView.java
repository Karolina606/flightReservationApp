package com.client.views.admin;

//import com.controller.FlightController;
import com.client.EmployeeRestClient;
import com.client.FlightRestClient;
import com.model.Flight;
import com.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Loty")
@Route(value = "flightRestApi")
public class FlightView extends VerticalLayout {

    Grid<Flight> grid = new Grid<>(Flight.class);
    TextField filterText = new TextField();
    FlightForm form;
    CrewView crewView = new CrewView();

    // Navigation
    Button logoutBtn = new Button("Wyloguj");
    Button employeesNavigateBtn = new Button("Pracownicy");
    Button planesNavigateBtn = new Button("Samoloty");
    Button flightsNavigateBtn = new Button("Loty");
    Button personalDataBtn = new Button("Dane osobowe");

    Notification notification = new Notification();

    public FlightView(){
        add(new H2("Loty"));

        setSizeFull();

        configureGrid();
        configureForm();
        crewView.setVisible(false);
        crewView.setSizeFull();

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
        HorizontalLayout content = new HorizontalLayout(grid, crewView, form);
        content.setFlexGrow(2, grid);
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

    private Component createNavigateButtonLayout() {
        employeesNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        planesNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        flightsNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        personalDataBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        employeesNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("employeesRestApi"));
        planesNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("planesRestApi"));
        flightsNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("flightRestApi"));
        personalDataBtn.addClickListener(event -> UI.getCurrent().navigate("personalDataRestApi"));
        logoutBtn.addClickListener(event -> logout());
        return new HorizontalLayout(employeesNavigateBtn, planesNavigateBtn, flightsNavigateBtn, personalDataBtn, logoutBtn);
    }

    public void logout(){
        SecurityService ss = new SecurityService();
        ss.logout();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Lot...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addFlightBtn = new Button("Zarządzaj lotami");
        addFlightBtn.addClickListener(event -> showHideFlightManager());

        Button showCrewBtn = new Button("Pokaż załogę");
        showCrewBtn.addClickListener(event -> showHideCrewManager());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addFlightBtn, showCrewBtn, createNavigateButtonLayout());
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void showHideCrewManager() {
        crewView.setVisible(!crewView.isVisible());
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

        grid.addColumn(new ComponentRenderer<>(flight -> {
                    Button showCrewBtn = new Button("Pokaż załogę");
                    showCrewBtn.addClickListener(click -> {
                        crewView.setFlight(flight);
                        crewView.setVisible(true);
                    });
                    showCrewBtn.setWidth("100%");

                    HorizontalLayout editLayout = new HorizontalLayout(showCrewBtn);
                    editLayout.setWidth("100%");
                    return editLayout;
                }))
                .setHeader("Pokaż załogę");

        grid.addColumn(new ComponentRenderer<>(flight -> {
                    Button deleteBtn = new Button("Usuń");
                    deleteBtn.addClickListener(click -> {
                        try{
                            FlightRestClient.callDeleteFlightApi(flight.getId());
                            System.out.println("ID lotu " + flight.getId());
                            updateList();

                            notification = Notification.show("Lot został usunięty.");
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        }catch(Exception e){
                            notification = Notification.show("Lot nie został usunięty.");
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        }
                    });
                    deleteBtn.setWidth("100%");

                    HorizontalLayout editLayout = new HorizontalLayout(deleteBtn);
                    editLayout.setWidth("100%");
                    return editLayout;
                }))
                .setHeader("Usuń");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
