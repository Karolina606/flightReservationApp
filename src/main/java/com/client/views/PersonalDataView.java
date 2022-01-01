package com.client.views;

//import com.controller.PersonalDataController;
import com.client.PersonalDataRestClient;
import com.controller.PersonalDataService;
import com.model.PersonalData;
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

@PageTitle("Dane osobowe")
@Route(value = "personalDataRestApi")
public class PersonalDataView extends VerticalLayout {

    private PersonalDataService service;
    Grid<PersonalData> grid = new Grid<>(PersonalData.class);
    TextField filterText = new TextField();
    //PersonalDataController controller = new PersonalDataController();
    PersonalDataForm form;

    public PersonalDataView(PersonalDataService service){
        this.service = service;
        // Collection<PersonalData> personalData = controller.getPersonalData();

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

    private Component getToolbar() {
        filterText.setPlaceholder("Nazwisko...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPersonalDataBtn = new Button("Manage personal data");
        addPersonalDataBtn.addClickListener(event -> showHidePersonalDataManager());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPersonalDataBtn);
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
        ).setHeader("apartment");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
