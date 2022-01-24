package com.client.views.admin;

import com.client.EmployeeRestClient;
import com.client.PersonalDataRestClient;
import com.model.EmployeeEnum;
import com.model.Employee;
import com.model.PersonalData;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;

public class EmployeeForm extends FormLayout {
    TextField pesel = new TextField("Pesel");
    TextField salary = new TextField("Pensja");
    ComboBox<EmployeeEnum> role = new ComboBox<>("Rola");

    TextField employeeId = new TextField("Pracownik id");

    Button save = new Button("Zapisz");
    Notification notification;

    EmployeeView employeeViewParent;
    public EmployeeForm(EmployeeView employeeViewParent){
        this.employeeViewParent = employeeViewParent;
        pesel.setRequired(true);
        salary.setRequired(true);
        role.setRequired(true);
        role.setItems(EmployeeEnum.values());
        add(pesel, salary, role, createButtonLayout());
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);

        save.addClickListener(event -> addEmployee());
        return new HorizontalLayout(save);
    }

    private void deleteEmployee() {
        Long employeeToDeleteId = Long.parseLong(employeeId.getValue());
        EmployeeRestClient.callDeleteEmployeeApi(employeeToDeleteId);
        System.out.println("Niby usunalem");
        employeeViewParent.updateList();
    }

    private void addEmployee() {
        try{
            Long newPesel = Long.parseLong(pesel.getValue());
            BigDecimal newSalary = BigDecimal.valueOf(Float.parseFloat(salary.getValue()));
            EmployeeEnum newRole = role.getValue();

            PersonalData newPersonalData = PersonalDataRestClient.callGetPersonalDataByIdApi(newPesel);
            if(newPersonalData == null){
                System.err.println("Nie ma danych personalnych z numerem pesel= " + newPesel);
                return ;
            }

            Employee employee = new Employee(newPersonalData, newRole, newSalary);

            // Jeśli pesel już w bazie zmodyfikuj
            if (EmployeeRestClient.callGetEmployeeByIdApi(newPesel) != null){
                EmployeeRestClient.callUpdateEmployeeApi(employee);
            }else{
                if (EmployeeRestClient.callCreateEmployeeApi(employee) ){
                    notification = Notification.show("Udało się dodać pracownika.");
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }else{
                    notification = Notification.show("Nie udało się dodać pracownika. Sprawdź poprawność danych.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        }catch(Exception e){
            notification = Notification.show("Nie udało się dodać pracownika, sprawdź poprawność danych.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

        employeeViewParent.updateList();
    }
}
