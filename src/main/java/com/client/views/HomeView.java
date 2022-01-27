package com.client.views;

import com.client.UserRestClient;
import com.model.PersonalData;
import com.model.User;
import com.model.UserRole;
import com.security.CustomUserDetails;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

@Route("home")
@PageTitle("Home | Flight Reservation App")
public class HomeView extends VerticalLayout {
    User user;
    UserRole role;

    Button userFlights = new Button("Loty");
    Button userReservation = new Button("Rezerwacje");

    Button adminFlights = new Button("LotyAdmin");
    Button adminEmploees = new Button("Pracownicy");
    Button adminPerosnalData = new Button("Dane personalne");
    Button adminPlanes = new Button("Samoloty");

    public HomeView(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        user = UserRestClient.callGetUserByLoginApi(username);
        role = user.getRole();
        String roleString = role.toString();

        System.out.println(role.toString());
        if(roleString.equals("PASSENGER")) {
            adminFlights.setVisible(false);
            adminEmploees.setVisible(false);
            adminPerosnalData.setVisible(false);
            adminPlanes.setVisible(false);
        }
        else if (roleString.equals("ADMIN")){
            userFlights.setVisible(false);
            userReservation.setVisible(false);
        }

        add(
                createLayout()
        );

    }

    Component createLayout(){
        userFlights.addClickListener(event-> UI.getCurrent().navigate("flightForUserRestApi"));
        userReservation.addClickListener(event-> UI.getCurrent().navigate("reservationRestApi"));

        adminFlights.addClickListener(event-> UI.getCurrent().navigate("flightRestApi"));
        adminEmploees.addClickListener(event-> UI.getCurrent().navigate("employeesRestApi"));
        adminPerosnalData.addClickListener(event-> UI.getCurrent().navigate("personalDataRestApi"));
        adminPlanes.addClickListener(event-> UI.getCurrent().navigate("planesRestApi"));

        return new HorizontalLayout(userFlights,userReservation,adminFlights,adminEmploees,adminPlanes,adminPerosnalData);
    }
}
