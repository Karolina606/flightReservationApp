package com.client.views.admin;

import com.client.EmployeeRestClient;
import com.model.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.transaction.Transactional;
import java.util.List;

public class CrewForm extends FormLayout {
    ComboBox<EmployeeEnum> role = new ComboBox<>("Rola");
    ComboBox<Employee> person = new ComboBox<>("Osoba");

    Button save = new Button("Zapisz");

    List<Employee> allPilots;
    List<Employee> allStewardess;

    private Flight flight;
    CrewView parentCrewView;
    Notification notification;

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
        if(EmployeeRestClient.callAddEmployeeToFlightCrew(newPerson, flight.getId())){
            notification = Notification.show("Udało się dodać obsługę do lotu.");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }else {
            notification = Notification.show("Nie udało się dokonać osoby do lotu. Albo za dużo godzin w miesiącu, albo nie ma miejsca w tym locie.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        parentCrewView.updateList();
    }

    private void roleChange() {
        if (role.getValue() == EmployeeEnum.PILOT){
            person.setItems(allPilots);
            person.setItemLabelGenerator(employee -> employee.getPersonalData().getFirstName() + " " + employee.getPersonalData().getLastName());
        }

        if (role.getValue() == EmployeeEnum.STEWARDESS){
            person.setItems(allStewardess);
            person.setItemLabelGenerator(employee -> employee.getPersonalData().getFirstName() + " " + employee.getPersonalData().getLastName());
        }
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}
