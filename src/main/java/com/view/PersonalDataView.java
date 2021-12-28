package com.view;

//import com.controller.PersonalDataController;
import com.controller.PersonalDataService;
import com.model.PersonalData;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collection;

@PageTitle("personalDataView")
@Route(value = "personalData")
public class PersonalDataView extends VerticalLayout {

    private PersonalDataService service;
    Grid<PersonalData> grid = new Grid<>(PersonalData.class);
    TextField filterText = new TextField();
    //PersonalDataController controller = new PersonalDataController();
    PersonalDataForm form;

    public PersonalDataView(PersonalDataService service){
        this.service = service;
        // Collection<PersonalData> personalData = controller.getPersonalData();

        // add(new H1("Hello world!"));

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

    private void updateList() {
        grid.setItems(service.findAllData(filterText.getValue()));
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
        form = new PersonalDataForm(service);
        form.setWidth("25em");
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Nazwisko...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPersonalData = new Button("Add personal data");
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPersonalData);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("personal-data-grid");
        grid.setSizeFull();
        grid.setColumns("pesel", "dateOfBirth", "firstName", "lastName", "phoneNumber");
        grid.addColumn(personalData -> personalData.getAddress().getCountry()).setHeader("country");
        grid.addColumn(personalData -> personalData.getAddress().getCity()).setHeader("city");
        grid.addColumn(personalData -> personalData.getAddress().getPostcode()).setHeader("postcode");
        grid.addColumn(personalData -> personalData.getAddress().getStreet()).setHeader("street");
        grid.addColumn(personalData -> personalData.getAddress().getBuildingNr()).setHeader("building");
        grid.addColumn(
                personalData -> personalData.getAddress().getApartmentNr() == null ?
                        "Empty" :
                        personalData.getAddress().getApartmentNr()
        ).setHeader("apartment");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
