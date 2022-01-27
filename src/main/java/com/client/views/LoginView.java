package com.client.views;

import com.client.UserRestClient;
import com.controller.UserController;
import com.model.User;
import com.model.UserRole;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Route("login")
@PageTitle("Login | Flight Reservation App")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    //Rejestracja
    TextField login = new TextField("Login");
    PasswordField passwordField = new PasswordField("Hasło");
    TextField adminCode = new TextField("Kod weryfikacji");

    Button registerBT = new Button("Register");
    Button showAdminVerificationButton = new Button("Admin verification");

    VerticalLayout registerForm;

    private LoginForm  loginForm = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        passwordField.setLabel("Password");
        passwordField.setHelperText("Hasło musi mieć przynajmniej 1 znak specjalny,1 dużą literę i jedną cyfrę i być długości 8 znaków");
        passwordField.setPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*\\_\\-\\=\\+]).{8}.*$");
        passwordField.setErrorMessage("Not a valid password");

        registerForm = new VerticalLayout(new H1("Rejestracja"), login,passwordField,adminCode,
                createButtonLayout());
        registerForm.setVisible(false);
        registerForm.setWidth("312px");
        registerForm.setAlignItems(Alignment.STRETCH);

        adminCode.setVisible(false);

        Button showRegisterFormButton = new Button("Show register form");
        showRegisterFormButton.addClickListener(event -> showRegisterForm());

        add(
                new H1("Flight Reservation App"),
                new H1("Logowanie"),
                loginForm,
                showRegisterFormButton,
                registerForm
        );
    }

    private void showRegisterForm() {
        registerForm.setVisible(!registerForm.isVisible());
    }

    private void showAdminCode() {
        adminCode.setVisible(!adminCode.isVisible());
    }

    private Component createButtonLayout() {
        registerBT.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerBT.addClickListener(event -> addUser());
        showAdminVerificationButton.addClickListener(e -> showAdminCode());

        return new HorizontalLayout(registerBT, showAdminVerificationButton);
    }


    private void addUser(){
        //user
        String newLogin = login.getValue();
        String newPassword = passwordField.getValue();
        String newCode = adminCode.getValue();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        UserRole role = UserRole.PASSENGER;

        String helperTxt  = "Zarejestrowano poprawnie, teraz możesz się zalogować.";

        User newUser = new User(newLogin,encodedPassword,null,role);

        if(!passwordField.isInvalid() && UserRestClient.callGetUserByLoginApi(newLogin) == null) {
            Notification notification;

            if(newCode.equals("c9MPsue8")) {    //default admin registration key
                newUser.setRole(UserRole.ADMIN);
            }
            else if(!newCode.isEmpty()) {
                notification = Notification.show("Nieprawidłowy kod weryfikacyjny.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            try{
                UserRestClient.callCreateUserApi(newUser);
                System.out.println("adding user");
                passwordField.setHelperText(helperTxt);
                notification = Notification.show(helperTxt);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }catch (Exception e){
                notification = Notification.show("Coś poszło nie tak");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        }

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")){
            loginForm.setError(true);
            Notification notification = Notification.show("Nie udało się zalogować.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

    }
}
