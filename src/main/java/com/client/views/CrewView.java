package com.client.views;

//import com.controller.EmployeeController;
import com.client.EmployeeRestClient;
import com.client.FlightRestClient;
import com.model.Employee;
import com.model.Flight;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

public class CrewView extends VerticalLayout {
    Grid<Employee> grid = new Grid<>(Employee.class);
    EmployeeForm form;
    private Flight flight;
    CrewForm crewForm = new CrewForm(this);

    public CrewView(){
        // Collection<Employee> Employee = controller.getEmployee();

        add(new H3("Załoga lotu"));

        setSizeFull();

        configureGrid();

        Component crew = getContent();
        VerticalLayout content = new VerticalLayout(crew, crewForm);
        content.setFlexGrow(3, crew);
        content.setFlexGrow(1, crewForm);

        add(content);

        //updateList();
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        crewForm.setFlight(flight);
        updateList();
    }

    public void updateList() {
        //grid.setItems(service.findAllData(filterText.getValue()));
        List<Employee> crew = flight.getCrew();
        if (crew != null){
            grid.setItems(crew);
        }
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        //content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

//    private void configureForm() {
//        form = new EmployeeForm(this);
//        form.setWidth("25em");
//        form.setVisible(false);
//    }

//    private Component getToolbar() {
//        filterText.setPlaceholder("Nazwisko...");
//        filterText.setClearButtonVisible(true);
//        filterText.setValueChangeMode(ValueChangeMode.LAZY);
//        filterText.addValueChangeListener(e -> updateList());
//
//        Button addEmployeeBtn = new Button("Manage employee");
//        addEmployeeBtn.addClickListener(event -> showHideEmployeeManager());
//        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeBtn);
//        toolbar.addClassName("toolbar");
//
//        return toolbar;
//    }

    private void showHideEmployeeManager() {
        form.setVisible(!form.isVisible());
    }


    private void configureGrid() {
        grid.setSizeFull();
        grid.setHeight("300px");
        grid.setColumns("id");
        grid.addColumn(Employee -> Employee.getPersonalData().getFirstName()).setHeader("FirstName");
        grid.addColumn(Employee -> Employee.getPersonalData().getLastName()).setHeader("LastName");
        grid.addColumn(Employee::getEmpolyeeRole).setHeader("EmployeeRole");
        grid.addColumn(Employee -> Employee.getPersonalData().getPhoneNumber()).setHeader("Phone");

//        grid.addColumn(new ComponentRenderer<>(employee -> {
//                    Button deleteBtn = new Button("Delete");
//                    deleteBtn.addClickListener(click -> {
//                        EmployeeRestClient.callDeleteEmployeeApi(employee.getId());
//                        updateList();
//                    });
//                    deleteBtn.setWidth("100%");
//
//                    HorizontalLayout editLayout = new HorizontalLayout(deleteBtn);
//                    editLayout.setWidth("100%");
//                    return editLayout;
//                }))
//                .setHeader("Delete");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
