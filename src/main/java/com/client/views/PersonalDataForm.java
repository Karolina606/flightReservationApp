package com.client.views;

import com.client.AddressRestClient;
import com.client.PersonalDataRestClient;
import com.client.views.admin.PersonalDataView;
import com.controller.PersonalDataService;
import com.model.Address;
import com.model.PersonalData;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PersonalDataForm extends FormLayout {
//    Input pesel = new Input();
//    Input dateOfBirth  = new Input();
//    Input firstName = new Input();
//    Input lastName = new Input();
//    Input phoneNumber = new Input();

    TextField pesel = new TextField("Pesel");
    DatePicker dateOfBirth  = new DatePicker("Data urodzenia");
    TextField firstName = new TextField("Imie");
    TextField lastName = new TextField("Nazwisko");
    TextField phoneNumber = new TextField("Numer telefonu");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancle = new Button("Cancle");

    PersonalDataView personalDataViewParent;
    AddressForm addressForm;
    Notification notification;

    public PersonalDataForm(PersonalDataView personalDataViewParent){
        this.personalDataViewParent = personalDataViewParent;
        addClassName("personal-data-form");

        pesel.setRequired(true);
        pesel.setId("input1");
        dateOfBirth.setRequiredIndicatorVisible(true);
        firstName.setRequired(true);
        lastName.setRequired(true);
        phoneNumber.setRequired(true);

        addressForm = new AddressForm();
        add(pesel, dateOfBirth, firstName, lastName, phoneNumber, addressForm, createButtonLayout());
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
        PersonalDataRestClient.callDeletePersonalDataApi(PersonalDataPesel);
        personalDataViewParent.updateList();
    }

    private void addPersonalData() {
        try{
            Long newPesel = Long.parseLong(pesel.getValue());
            String newFirstName = firstName.getValue();
            String newLastName = lastName.getValue();
            Long newPhoneNumber = Long.parseLong(phoneNumber.getValue());

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newBirthDate = dateOfBirth.getValue();

            // if address already exists
            Address newAddress = addressForm.createAddress();
            Address foundAddress = AddressRestClient.returnIfAddressInDataBase(newAddress);
            Address addressToSave;
            if ( foundAddress == null){
                addressToSave = newAddress;
                AddressRestClient.callCreateAddressApi(addressToSave);
            }else{
                addressToSave = foundAddress;
            }

            PersonalData personalData = new PersonalData(newPesel, newFirstName, newLastName, newBirthDate, newPhoneNumber, addressToSave);

            // Jeśli pesel już w bazie zmodyfikuj
            if (PersonalDataRestClient.callGetPersonalDataByIdApi(newPesel) != null){
                PersonalDataRestClient.callUpdatePersonalDataApi(personalData);
            }else{
                if(PersonalDataRestClient.callCreatePersonalDataApi(personalData)){
                    notification = Notification.show("Udało się dodać dane osobowe.");
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }else{
                    notification = Notification.show("Nie udało się dodać dane osobowe.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        }catch(Exception e){
            notification = Notification.show("Nie udało się dodać dane osobowe.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

        personalDataViewParent.updateList();
    }
}
