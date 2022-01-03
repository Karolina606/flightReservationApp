package com.client.views;

import com.client.EmployeeRestClient;
import com.client.FlightRestClient;
import com.model.*;
import com.modelsRepos.EmployeeRepo;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import javax.transaction.Transactional;
import java.util.List;

public class CrewForm extends FormLayout {
    ComboBox<EmployeeEnum> role = new ComboBox<>("Rola");
    ComboBox<Employee> person = new ComboBox<>("Osoba");

    Button save = new Button("Save");

    List<Employee> allPilots;
    List<Employee> allStewardess;

    private Flight flight;
    CrewView parentCrewView;

    public CrewForm(CrewView parentCrewView){
        this.parentCrewView = parentCrewView;

        allPilots = EmployeeRestClient.callGetEmployeeWithRoleApi(EmployeeEnum.PILOT.ordinal());
        allStewardess = EmployeeRestClient.callGetEmployeeWithRoleApi(EmployeeEnum.STEWARDESS.ordinal());
        role.setItems(EmployeeEnum.values());
        role.addValueChangeListener(event -> roleChange());

        save.addClickListener(event -> savePersonToCrew());

        HorizontalLayout horizontalLayout = new HorizontalLayout(role, person);
        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, save);
        add(verticalLayout);
    }

    @Transactional
    public void savePersonToCrew() {
        Employee newPerson = person.getValue();
        EmployeeRestClient.callAddEmployeeToFlightCrew(newPerson, flight.getId());
//        flight.getCrew().add(newPerson);
//        FlightRestClient.callUpdateFlightApi(flight);
//        EmployeeRestClient.callUpdateEmployeeApi(newPerson);
        System.out.println("Udalo sie dodac osobe do lotu");
        parentCrewView.updateList();
    }

    private void roleChange() {
        if (role.getValue() == EmployeeEnum.PILOT){
            person.setItems(allPilots);
            person.setItemLabelGenerator(employee -> employee.getPersonalData().getFirstName() + " " + employee.getPersonalData().getLastName());
        }

        if (role.getValue() == EmployeeEnum.STEWARDESS){
            person.setItems(allStewardess);
            person.setItemLabelGenerator(employee -> employee.getPersonalData().getFirstName() + " " + employee.getPersonalData().getFirstName());
        }
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}
