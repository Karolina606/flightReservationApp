package com;

import cn.licoy.encryptbody.annotation.EnableEncryptBody;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableEncryptBody
@SpringBootApplication
@ComponentScan({"com.controller"})
@ComponentScan({"com.client.views"})
@ComponentScan({"com"})
public class FlightReservationAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightReservationAppApplication.class, args);
    }
}
