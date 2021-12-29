package com.client;

import com.controller.PersonalDataService;
import com.model.Address;
import com.model.PersonalData;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PersonalDataForm extends FormLayout {
    TextField pesel = new TextField("Pesel");
    TextField dateOfBirth = new TextField("Data urodzenia");
    TextField firstName = new TextField("Imie");
    TextField lastName = new TextField("Nazwisko");
    TextField phoneNumber = new TextField("Numer telefonu");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancle = new Button("Cancle");

    PersonalDataService service;

    PersonalDataView personalDataViewParent;

    public PersonalDataForm(PersonalDataService service, PersonalDataView personalDataViewParent){
        this.service = service;
        this.personalDataViewParent = personalDataViewParent;
        addClassName("personal-data-form");

        add(pesel, dateOfBirth, firstName, lastName, phoneNumber, createButtonLayout());
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancle.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> addPersonalData());
        delete.addClickListener(event -> deletePersonData());
        return new HorizontalLayout(save, delete, cancle);
    }

    private void deletePersonData() {
        Long PersonalDataPesel = Long.parseLong(pesel.getValue());
        RestClient.callDeletePersonalDataApi(PersonalDataPesel);
        personalDataViewParent.updateList();
//        UI.getCurrent().getPage().reload();
    }

    private void addPersonalData() {
        Long newPesel = Long.parseLong(pesel.getValue());
        String newFirstName = firstName.getValue() ;
        String newLastName = lastName.getValue();
        Long newPhoneNumber = Long.parseLong(phoneNumber.getValue());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newBirthDate = LocalDate.parse(dateOfBirth.getValue(), df);

        Address address = service.findAddressById(1L);
        PersonalData personalData = new PersonalData(newPesel, newFirstName, newLastName, newBirthDate, newPhoneNumber, address);

        // Jeśli pesel już w bazie zmodyfikuj
        if (RestClient.callGetPersonalDataByIdApi(newPesel) != null){
            RestClient.callUpdatePersonalDataApi(personalData);
        }else{
            RestClient.callCreatePersonalDataApi(personalData);
            //service.savePersonalData(personalData);
        }
        personalDataViewParent.updateList();
//        UI.getCurrent().getPage().reload();
    }
}
