package com.client.views;

import com.client.AddressRestClient;
import com.model.Address;
import com.model.Address;
import com.model.PersonalData;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddressForm extends FormLayout {
    TextField country = new TextField("Kraj");
    TextField city = new TextField("Miasto");
    TextField postcode = new TextField("Kod pocztowy");
    TextField street = new TextField("Ulica");
    TextField buildingNr = new TextField("Numer budynku");
    TextField apartmentNr = new TextField("Numer mieszkania");

    public AddressForm(){
        add(country, city, postcode, street, buildingNr, apartmentNr);
    }


    public void setAddress(PersonalData personalData){
        country.setValue(personalData.getAddress().getCountry());
        city.setValue(personalData.getAddress().getCity());
        postcode.setValue(personalData.getAddress().getPostcode());
        street.setValue(personalData.getAddress().getStreet());
        buildingNr.setValue(Integer.toString(personalData.getAddress().getBuildingNr()));
        apartmentNr.setValue(Integer.toString(personalData.getAddress().getApartmentNr()));
    }

    public Address createAddress(){
        String newCountry = country.getValue() ;
        String newCity = city.getValue();
        String newPostcode = postcode.getValue();
        String newStreet = street.getValue();
        int newBuildingNumber = Integer.parseInt(buildingNr.getValue());
        Integer newApartmentNumber = Integer.parseInt(apartmentNr.getValue());

        return new Address(newCountry, newCity, newPostcode, newStreet, newBuildingNumber, newApartmentNumber);
    }
}
