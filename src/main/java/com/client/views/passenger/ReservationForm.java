package com.client.views.passenger;

import com.client.AddressRestClient;
import com.client.PersonalDataRestClient;
import com.client.ReservationRestClient;
import com.client.UserRestClient;
import com.client.views.AddressForm;
import com.model.*;
import com.security.CustomUserDetails;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationForm extends FormLayout {
    TextField pesel = new TextField("Pesel");

    DatePicker dateOfBirth  = new DatePicker("Data urodzenia");
    TextField firstName = new TextField("Imie");
    TextField lastName = new TextField("Nazwisko");
    TextField phoneNumber = new TextField("Numer telefonu");

    TextField country = new TextField("Kraj");
    TextField city = new TextField("Miasto");
    TextField postcoode = new TextField("Kod pocztowy");
    TextField street = new TextField("Ulica");
    TextField building_nr = new TextField("Numer budynku");
    TextField apartment_nr = new TextField("Numer mieszkania");

    Button save = new Button("Save");
    Button delete = new Button("Delete");

    //PersonalDataService PersonalDataService;
    Flight currentFlight;

    AddressForm addressForm;

    // Dane użytkownika
    User user;
    PersonalData personalData;

    public ReservationForm(){
        // Użytkownik
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        user = UserRestClient.callGetUserByLoginApi(username);
        personalData = user.getPersonalData();

        //
        addClassName("personal-data-form");

        pesel.setRequired(true);
        dateOfBirth.setRequiredIndicatorVisible(true);
        firstName.setRequired(true);
        lastName.setRequired(true);
        phoneNumber.setRequired(true);

        addressForm = new AddressForm();

        // jeśli dane osobowe nie są puste
        if (personalData != null){
            pesel.setValue(personalData.getPesel().toString());
            dateOfBirth.setValue(personalData.getDateOfBirth());
            firstName.setValue(personalData.getFirstName());
            lastName.setValue(personalData.getLastName());
            phoneNumber.setValue(personalData.getPhoneNumber().toString());

            addressForm.setAddress(personalData);
        }

        add(pesel, dateOfBirth, firstName, lastName, phoneNumber, addressForm, createButtonLayout());
    }

    public void setCurrentFlight(Flight currentFlight) {
        this.currentFlight = currentFlight;
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> addReservation());
        return new HorizontalLayout(save);
    }

//    private void deletePersonData() {
//        Long PersonalDataPesel = Long.parseLong(pesel.getValue());
//        PersonalDataRestClient.callDeletePersonalDataApi(PersonalDataPesel);
//        personalDataViewParent.updateList();
//    }

    private void addReservation() {
        if (personalData == null){
            Long newPesel = Long.parseLong(pesel.getValue());
            String newFirstName = firstName.getValue();
            String newLastName = lastName.getValue();
            Long newPhoneNumber = Long.parseLong(phoneNumber.getValue());

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newBirthDate = dateOfBirth.getValue();

            //Address address = service.findAddressById(1L);

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
            user.setPersonalData(personalData);
            UserRestClient.callUpdateUserApi(user);

            // Jeśli pesel już w bazie zmodyfikuj
            if (PersonalDataRestClient.callGetPersonalDataByIdApi(newPesel) != null){
                PersonalDataRestClient.callUpdatePersonalDataApi(personalData);
            }else{
                PersonalDataRestClient.callCreatePersonalDataApi(personalData);
                //service.savePersonalData(personalData);
            }
        }

        Reservation newReservation = new Reservation(currentFlight, personalData);
        ReservationRestClient.callCreateReservationApi(newReservation);
    }
}
