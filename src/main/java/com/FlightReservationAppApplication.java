package com;


import com.model.*;
import com.view.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.controller"})
@ComponentScan({"com"})
@ComponentScan({"com.client.views"})
public class FlightReservationAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightReservationAppApplication.class, args);
    }
}
