package com.client.views.passenger;

//import com.controller.FlightController;
import com.client.FlightRestClient;
import com.client.views.PersonalDataForm;
import com.client.views.admin.CrewView;
import com.client.views.admin.FlightForm;
import com.model.Flight;
import com.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@PageTitle("Loty")
@Route(value = "flightForUserRestApi")
public class FlightView extends VerticalLayout {

    Grid<Flight> grid = new Grid<>(Flight.class);
    TextField filterText = new TextField();
    ReservationForm form;

    Button logoutBtn = new Button("Wyloguj");
    Button userReservationBtn = new Button("Moje rezerwacje");
    Button flightsBtn = new Button("Loty");

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
        content.setFlexGrow(2, grid);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private Component createNavigateButtonLayout() {
        flightsBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        userReservationBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        flightsBtn.addClickListener(event -> UI.getCurrent().navigate("flightForUserRestApi"));
        userReservationBtn.addClickListener(event -> UI.getCurrent().navigate("reservationRestApi"));
        logoutBtn.addClickListener(event -> logout());
        return new HorizontalLayout(flightsBtn, userReservationBtn, logoutBtn);
    }

    public void logout(){
        SecurityService ss = new SecurityService();
        ss.logout();
    }

    private void configureForm() {
        form = new ReservationForm();
        form.setWidth("25em");
        form.setVisible(false);
    }


    private Component getToolbar() {
        filterText.setPlaceholder("Lot...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Component buttonLayout = createNavigateButtonLayout();
        HorizontalLayout toolbar = new HorizontalLayout(filterText, buttonLayout);

        toolbar.addClassName("toolbar");

        return toolbar;
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
                    Button reserveBtn = new Button("Zarezerwuj");
                    reserveBtn.addClickListener(click -> {
                        form.setCurrentFlight(flight);
                        form.setVisible(true);
                    });
                    reserveBtn.setWidth("100%");

                    HorizontalLayout editLayout = new HorizontalLayout(reserveBtn);
                    editLayout.setWidth("100%");
                    return editLayout;
                }))
                .setHeader("Reserve");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
