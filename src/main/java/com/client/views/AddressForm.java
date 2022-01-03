package com.client.views;

import com.client.AddressRestClient;
import com.model.Address;
import com.model.Address;
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

    private void deleteAddress() {
//        Long addressId = Long.parseLong(addres.getValue());
//        AddressRestClient.callDeleteAddressApi(AddressPesel);
//        addressViewParent.updateList();
    }

    public Address createAddres(){
        String newCountry = country.getValue() ;
        String newCity = city.getValue();
        String newPostcode = postcode.getValue();
        String newStreet = street.getValue();
        int newBuildingNumber = Integer.parseInt(buildingNr.getValue());
        Integer newApartmentNumber = Integer.parseInt(apartmentNr.getValue());

        return new Address(newCountry, newCity, newPostcode, newStreet, newBuildingNumber, newApartmentNumber);
    }

//    private void addAddress() {
//        Long newPesel = Long.parseLong(pesel.getValue());
//        String newFirstName = firstName.getValue() ;
//        String newLastName = lastName.getValue();
//        Long newPhoneNumber = Long.parseLong(phoneNumber.getValue());
//
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate newBirthDate = LocalDate.parse(dateOfBirth.getValue(), df);
//
//        Address address = service.findAddressById(1L);
//        Address address = new Address(newPesel, newFirstName, newLastName, newBirthDate, newPhoneNumber, address);
//
//        // Jeśli pesel już w bazie zmodyfikuj
//        if (AddressRestClient.callGetAddressByIdApi(newPesel) != null){
//            AddressRestClient.callUpdateAddressApi(address);
//        }else{
//            AddressRestClient.callCreateAddressApi(address);
//            //service.saveAddress(address);
//        }
//        addressViewParent.updateList();
//    }
}
