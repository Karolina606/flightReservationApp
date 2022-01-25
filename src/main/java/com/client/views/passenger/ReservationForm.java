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
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ReservationForm extends FormLayout {
    TextField pesel = new TextField("Pesel");

    DatePicker dateOfBirth  = new DatePicker("Data urodzenia");
    TextField firstName = new TextField("Imie");
    TextField lastName = new TextField("Nazwisko");
    TextField phoneNumber = new TextField("Numer telefonu");

    AddressForm addressForm;

    Checkbox selfReservationChB = new Checkbox("Wykonuję rezerwację dla kogoś");

    Button save = new Button("Zapisz");
    Button delete = new Button("Usuń");

    //PersonalDataService PersonalDataService;
    Flight currentFlight;


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

        add(pesel, dateOfBirth, firstName, lastName, phoneNumber, addressForm, selfReservationChB, createButtonLayout());
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

    private void addReservation() {
        Notification notification;
        // robisz komuś rezerwację
        if(selfReservationChB.getValue()){
            if (personalData != null){
                try{
                    if(Period.between(personalData.getDateOfBirth(), LocalDate.now()).getYears() >= 16){
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
                            //AddressRestClient.callCreateAddressApi(addressToSave);
                        }else{
                            addressToSave = foundAddress;
                        }

                        PersonalData personalData = new PersonalData(newPesel, newFirstName, newLastName, newBirthDate, newPhoneNumber, addressToSave);
                        // Jeśli pesel już w bazie zmodyfikuj
                        if (PersonalDataRestClient.callGetPersonalDataByIdApi(newPesel) != null){
                            PersonalDataRestClient.callUpdatePersonalDataApi(personalData);
                        }else{
                            if(PersonalDataRestClient.callCreatePersonalDataApi(personalData)){
                                notification = Notification.show("Udało się zapisać dane osobowe.");
                                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            }else{
                                notification = Notification.show("Nie udało się zapisać danych osobowych. Sprawdź poprawność danych.");
                                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            }
                        }

                        Reservation newReservation = new Reservation(currentFlight, personalData);
                        ReservationRestClient.callCreateReservationApi(newReservation);
                    }
                }catch(Exception e){
                    notification = Notification.show("Nie udało się dokonać rezerwacji. Sprawdź poprawność danych.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }else{
                notification = Notification.show("Musimy znać twoją datę urodzenia, żebyś mógł dokonać rezerwacji dla kogoś.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

        }
        // Wykonanie rezerwacji dla siebie
        else if(!selfReservationChB.getValue()) {
            personalData = user.getPersonalData();
            // Wykonanie rezerwacji dla siebie, gdy nie ma jeszcze danych personalnych w bazie
            if (personalData == null) {
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
                    if (foundAddress == null) {
                        addressToSave = newAddress;
                        //AddressRestClient.callCreateAddressApi(addressToSave);
                    } else {
                        addressToSave = foundAddress;
                    }

                    personalData = new PersonalData(newPesel, newFirstName, newLastName, newBirthDate, newPhoneNumber, addressToSave);

                    // Jeśli pesel już w bazie zmodyfikuj
                    if (PersonalDataRestClient.callGetPersonalDataByIdApi(newPesel) != null) {
                        PersonalDataRestClient.callUpdatePersonalDataApi(personalData);
                    } else {
                        if(PersonalDataRestClient.callCreatePersonalDataApi(personalData)){
                            notification = Notification.show("Udało się zapisać dane osobowe.");
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        }else{
                            notification = Notification.show("Nie udało się zapisać danych osobowych. Sprawdź poprawność danych.");
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        }
                    }

                    user.setPersonalData(personalData);
                    UserRestClient.callUpdateUserApi(user);

                    Reservation newReservation = new Reservation(currentFlight, personalData);
                    ReservationRestClient.callCreateReservationApi(newReservation);

                } catch(Exception e){
                    notification = Notification.show("Nie udało się dokonać rezerwacji. Sprawdź poprawność danych.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }

            // Gdy są już dane personalne w bazie
            else {
                Reservation newReservation = new Reservation(currentFlight, personalData);
                ReservationRestClient.callCreateReservationApi(newReservation);
            }
        }

        else{
            notification = Notification.show("Nie udało się dokonać rezerwacji.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
