package com.client.views.passenger;

//import com.controller.ReservationController;
import com.client.EmployeeRestClient;
import com.client.ReservationRestClient;
import com.client.UserRestClient;
import com.model.PersonalData;
import com.model.Reservation;
import com.model.User;
import com.security.CustomUserDetails;
import com.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Rezerwacje")
@Route(value = "reservationRestApi")
public class ReservationView extends VerticalLayout {

    Grid<Reservation> grid = new Grid<>(Reservation.class);
    TextField filterText = new TextField();

    // Dane użytkownika
    User user;
    PersonalData personalData;

    Button logoutBtn = new Button("Wyloguj");
    Button userReservationBtn = new Button("Moje rezerwacje");
    Button flightsBtn = new Button("Loty");

    public ReservationView(){
        // Użytkownik
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        user = UserRestClient.callGetUserByLoginApi(username);
        personalData = user.getPersonalData();

        add(new H2("Rezerwacje"));

        setSizeFull();

        configureGrid();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
    }

    public void updateList() {
        List<Reservation> reservationList = new ArrayList<Reservation>();
        if (personalData != null){
            reservationList = ReservationRestClient.callGetReservationByPeselApi(personalData.getPesel());
        }
        grid.setItems(reservationList);
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
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

    private Component getToolbar() {
        filterText.setPlaceholder("Rezerwacja...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, createNavigateButtonLayout());
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("id");
        grid.addColumn(reservation -> reservation.getFlight().getDeparturePlace().getName()).setHeader("DeparturePlace");
        grid.addColumn(reservation -> reservation.getFlight().getArrivalPlace().getName()).setHeader("ArrivalPlace");
        grid.addColumn(reservation -> reservation.getFlight().getDepartureDate()).setHeader("DepartureDate");
        grid.addColumn(reservation -> reservation.getFlight().getArrivalDate()).setHeader("ArrivalDate");
        grid.addColumn(reservation -> reservation.getFlight().getPrice()).setHeader("Price");
        grid.addColumn(reservation -> reservation.getFlight().getPlane().getAirlines()).setHeader("Airlines");
        grid.addColumn(reservation -> reservation.getFlight().getPlane().getModel().getBrand()).setHeader("Brand");
        grid.addColumn(reservation -> reservation.getFlight().getPlane().getModel().getModelName()).setHeader("Model");

        grid.addColumn(new ComponentRenderer<>(reservation -> {
                    Button deleteBtn = new Button("Usuń");
                    deleteBtn.addClickListener(click -> {
                        ReservationRestClient.callDeleteReservationApi(reservation.getId());
                        updateList();
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
