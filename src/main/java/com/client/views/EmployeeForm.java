package com.client.views;

import com.client.AddressRestClient;
import com.client.EmployeeRestClient;
import com.client.PersonalDataRestClient;
import com.model.Address;
import com.model.EmployeeEnum;
import com.model.Employee;
import com.model.PersonalData;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeForm extends FormLayout {
    TextField pesel = new TextField("Pesel");
    TextField salary = new TextField("Pensja");
    ComboBox<EmployeeEnum> role = new ComboBox<>("Rola");

    TextField employeeId = new TextField("Pracownik id");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancle = new Button("Cancle");

    EmployeeView employeeViewParent;
    public EmployeeForm(EmployeeView employeeViewParent){
        this.employeeViewParent = employeeViewParent;
        pesel.setRequired(true);
        salary.setRequired(true);
        role.setRequired(true);
        role.setItems(EmployeeEnum.values());
        employeeId.setRequired(true);

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(event -> deleteEmployee());
        add(pesel, salary, role, createButtonLayout(), employeeId, delete);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancle.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> addEmployee());
        //delete.addClickListener(event -> deleteEmployee());
        return new HorizontalLayout(save, cancle);
    }

    private void deleteEmployee() {
        Long employeeToDeleteId = Long.parseLong(employeeId.getValue());
        EmployeeRestClient.callDeleteEmployeeApi(employeeToDeleteId);
        System.out.println("Niby usunalem");
        employeeViewParent.updateList();
    }

    private void addEmployee() {
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
            EmployeeRestClient.callCreateEmployeeApi(employee);
            //service.saveEmployee(employee);
        }
        employeeViewParent.updateList();
    }
}
