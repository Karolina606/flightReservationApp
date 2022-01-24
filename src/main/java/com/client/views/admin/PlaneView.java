package com.client.views.admin;

//import com.controller.PlaneController;
import com.client.PlaneRestClient;
import com.model.Plane;
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

import java.util.List;

@PageTitle("Samoloty")
@Route(value = "planesRestApi")
public class PlaneView extends VerticalLayout {

    Grid<Plane> grid = new Grid<>(Plane.class);
    TextField filterText = new TextField();
    //PlaneController controller = new PlaneController();
    PlaneForm form;
    PlaneModelForm modelForm;

    // Navigation
    Button logoutBtn = new Button("Wyloguj");
    Button employeesNavigateBtn = new Button("Pracownicy");
    Button planesNavigateBtn = new Button("Samoloty");
    Button flightsNavigateBtn = new Button("Loty");
    Button personalDataBtn = new Button("Dane osobowe");

    public PlaneView(){
        // Collection<Plane> Plane = controller.getPlane();

        add(new H2("Samoloty"));

        //addClassName("personal-data-view");
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
        //grid.setItems(service.findAllData(filterText.getValue()));
        List<Plane> planes = PlaneRestClient.callGetAllPlaneApi();
        if (planes != null){
            grid.setItems(planes);
        }
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form, modelForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexGrow(1, modelForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new PlaneForm(this);
        form.setWidth("25em");
        form.setVisible(false);

        modelForm = new PlaneModelForm(this);
        modelForm.setWidth("25em");
        modelForm.setVisible(false);
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

        Button addPlaneBtn = new Button("Zarządzaj samolotami");
        Button addPlaneModelBtn = new Button("Zarządzaj modelami");

        addPlaneBtn.addClickListener(event -> showHidePlaneManager());
        addPlaneModelBtn.addClickListener(event -> showHidePlaneModelManager());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPlaneBtn, addPlaneModelBtn, createNavigateButtonLayout());
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void showHidePlaneModelManager() {
        modelForm.setVisible(!modelForm.isVisible());
    }

    private void showHidePlaneManager() {
        form.setVisible(!form.isVisible());
    }


    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("id", "airlines", "inspectionDate");
        grid.addColumn(Plane -> Plane.getModel().getBrand()).setHeader("Brand");
        grid.addColumn(Plane -> Plane.getModel().getModelName()).setHeader("Model");
        grid.addColumn(Plane -> Plane.getModel().getNumberOfPilots()).setHeader("Pilots");
        grid.addColumn(Plane -> Plane.getModel().getNumberOfFlightAttendants()).setHeader("FlightAttendants");
        grid.addColumn(Plane -> Plane.getModel().getNumberOfSeats()).setHeader("Seats");
        grid.addColumn(Plane -> Plane.getModel().getTankCapacity()).setHeader("TankCapacity");

        grid.addColumn(new ComponentRenderer<>(plane -> {
                    Button deleteBtn = new Button("Usuń");
                    deleteBtn.addClickListener(click -> {
                        PlaneRestClient.callDeletePlaneApi(plane.getId());
                        updateList();
                    });
                    deleteBtn.setWidth("100%");

                    HorizontalLayout editLayout = new HorizontalLayout(deleteBtn);
                    editLayout.setWidth("100%");
                    return editLayout;
                }))
                .setHeader("Delete");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
