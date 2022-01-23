package com.client.views.admin;

//import com.controller.PersonalDataController;
import com.client.PersonalDataRestClient;
import com.client.views.PersonalDataForm;
import com.controller.PersonalDataService;
import com.model.PersonalData;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Dane osobowe")
@Route(value = "personalDataRestApi")
public class PersonalDataView extends VerticalLayout {

    private PersonalDataService service;
    Grid<PersonalData> grid = new Grid<>(PersonalData.class);
    TextField filterText = new TextField();
    PersonalDataForm form;

    // Navigation
    Button logoutBtn = new Button("Wyloguj");
    Button employeesNavigateBtn = new Button("Pracownicy");
    Button planesNavigateBtn = new Button("Samoloty");
    Button flightsNavigateBtn = new Button("Loty");

    public PersonalDataView(){
        add(new H2("Dane osobowe"));

        addClassName("personal-data-view");
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
        grid.setItems(PersonalDataRestClient.callGetAllPersonalDataApi());
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
        form = new PersonalDataForm(service, this);
        form.setWidth("25em");
        form.setVisible(false);
    }

    private Component createNavigateButtonLayout() {
        employeesNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        planesNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        flightsNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        employeesNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("employeesRestApi"));
        planesNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("planesRestApi"));
        flightsNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("flightRestApi"));
        logoutBtn.addClickListener(event -> logout());
        return new HorizontalLayout(employeesNavigateBtn, planesNavigateBtn, flightsNavigateBtn, logoutBtn);
    }

    public void logout(){
        SecurityService ss = new SecurityService();
        ss.logout();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Nazwisko...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPersonalDataBtn = new Button("Manage personal data");
        addPersonalDataBtn.addClickListener(event -> showHidePersonalDataManager());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPersonalDataBtn, createNavigateButtonLayout());
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void showHidePersonalDataManager() {
        form.setVisible(!form.isVisible());
    }


    private void configureGrid() {
        grid.addClassName("personal-data-grid");
        grid.setSizeFull();
        grid.setColumns("pesel", "dateOfBirth", "firstName", "lastName", "phoneNumber");
        grid.addColumn(personalData -> personalData.getAddress().getCountry()).setHeader("Country");
        grid.addColumn(personalData -> personalData.getAddress().getCity()).setHeader("City");
        grid.addColumn(personalData -> personalData.getAddress().getPostcode()).setHeader("Postcode");
        grid.addColumn(personalData -> personalData.getAddress().getStreet()).setHeader("Street");
        grid.addColumn(personalData -> personalData.getAddress().getBuildingNr()).setHeader("Building");
        grid.addColumn(
                personalData -> personalData.getAddress().getApartmentNr() == null ?
                        "Empty" :
                        personalData.getAddress().getApartmentNr()
        ).setHeader("Apartment");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
