package com.model;

import com.modelsRepos.PersonalDataRepo;
import com.modelsRepos.PlaneModelRepo;
import com.modelsRepos.PlaneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Start {

    private PlaneRepo planeRepo;
    private PlaneModelRepo planeModelRepo;

    @Autowired
    public Start(PlaneRepo planeRepo, PlaneModelRepo planeModelRepo){
        this.planeRepo = planeRepo;
        this.planeModelRepo = planeModelRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExample(){
//        PlaneModel planeModel = new PlaneModel("Boeing", "737", 230, 2, 10, 25940f);
//        Date date = new Date();
//        Plane plane = new Plane(planeModel, "Ryanair", date);
//        planeModelRepo.save(planeModel);
//        planeRepo.save(plane);
        System.out.println("Działam jak coś");
    }
}
