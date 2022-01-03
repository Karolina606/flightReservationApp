package com.view;

import com.controller.AddressService;
import com.controller.PersonalDataService;
import com.controller.UserController;
import com.model.Address;
import com.model.PersonalData;
import com.model.User;
import com.model.UserRole;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Route("login")
@PageTitle("Login | Flight Reservation App")
public class LoginView extends VerticalLayout implements BeforeEnterListener {
//Rejestracja
    //Address
    TextField apartmentNr = new TextField("Numer mieszkania");
    TextField buildingNr = new TextField("Numer Budynku");
    TextField city = new TextField("Miasto");
    TextField country = new TextField("Kraj");
    TextField postcode = new TextField("Kod pocztowy");
    TextField street = new TextField("Ulica");
    //personal_data
    TextField pesel = new TextField("PESEL");
    TextField dateOfBirth = new TextField("Data urodzenia");
    TextField firstName = new TextField("Imie");
    TextField lastName = new TextField("Nazwisko");
    TextField phoneNumber = new TextField("Numer telefonu");
    //address_id addres.count() +1
    //user
    TextField login = new TextField("Login");
    TextField password = new TextField("Hasło");
    //role == PASSENGER
    //pesel już był

    Button registerBT = new Button("Register");

    UserController userController;
//    UserService userService;
    PersonalDataService pDService;
    AddressService aService;


    private LoginForm  loginForm = new LoginForm();
    public LoginView(UserController uController,PersonalDataService pdService,AddressService addressService){
        userController = uController;
        pDService = pdService;
        aService = addressService;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        add(
                new H1("Flight Reservation App"),
                new H1("Logowanie"),
                loginForm,
                new H1("Rejestracja"),
                apartmentNr,buildingNr,city,country,postcode,street,pesel,dateOfBirth,
                firstName,lastName,phoneNumber,login,password,
                createButtonLayout()
        );
    }
    private Component createButtonLayout() {
        registerBT.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        registerBT.addClickListener(event -> addUser());
        return new HorizontalLayout(registerBT);
    }

    private void addUser(){
        //address
        int newApartmentNr = Integer.parseInt(apartmentNr.getValue());
        int newBuildingNr = Integer.parseInt(buildingNr.getValue());
        String newCity = city.getValue();
        String newCountry = country.getValue();
        String newPostcode = postcode.getValue();
        String newStreet = street.getValue();

        Address newAddress = new Address(newCountry,newCity,newPostcode,newStreet,newBuildingNr,newApartmentNr);
        //personalData
        Long newPesel = Long.parseLong(pesel.getValue());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newBirthDate = LocalDate.parse(dateOfBirth.getValue(), df);
        String newFirstName = firstName.getValue();
        String newLastName = lastName.getValue();
        Long newPhoneNumber = Long.parseLong(phoneNumber.getValue());

        PersonalData newPersonalData = new PersonalData(newPesel, newFirstName, newLastName, newBirthDate, newPhoneNumber, newAddress);
        //user
        String newLogin = login.getValue();
        String newPassword = password.getValue();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        UserRole role = UserRole.PASSENGER;

        User newUser = new User(newLogin,encodedPassword,newPersonalData,role);

        //saving user
        aService.saveUser(newAddress);
        pDService.savePersonalData(newPersonalData);
        userController.createUser(newUser);
        System.out.println("adding user");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")){
            loginForm.setError(true);
        }

    }
}
