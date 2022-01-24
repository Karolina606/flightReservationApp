package com.view;

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
    //role == PASSENGER

    Button registerBT = new Button("Register");

    UserController userController;
    VerticalLayout registerForm;


    private LoginForm  loginForm = new LoginForm();
    public LoginView(UserController uController){
        userController = uController;
        addClassName("login-view");
        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        passwordField.setLabel("Password");
        passwordField.setHelperText("Hasło musi mieć przynajmniej 1 znak specjalny,1 dużą literę i jedną cyfrę i być długości 8 znaków");
        passwordField.setPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*\\_\\-\\=\\+]).{8}.*$");
        passwordField.setErrorMessage("Not a valid password");

        registerForm = new VerticalLayout(new H1("Rejestracja"), login,passwordField,
                createButtonLayout());
        registerForm.setVisible(false);
        registerForm.setWidth("312px");
        registerForm.setAlignItems(Alignment.STRETCH);

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

    private Component createButtonLayout() {
        registerBT.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        registerBT.addClickListener(event -> addUser());
        return new HorizontalLayout(registerBT);
    }

    private void addUser(){
        //user
        String newLogin = login.getValue();
        String newPassword = passwordField.getValue();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        UserRole role = UserRole.PASSENGER;

        User newUser = new User(newLogin,encodedPassword,null,role);

        if(!passwordField.isInvalid() && userController.getUserById(newLogin) == null) {
            userController.createUser(newUser);
            System.out.println("adding user");
            passwordField.setHelperText("Zarejestrowano poprawnie, teraz możesz się zalogować.");
            Notification notification = Notification.show("Zarejestrowano poprawnie, teraz możesz się zalogować.");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")){
            loginForm.setError(true);
        }

    }
}
