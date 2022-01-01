package com.controller;


import com.model.Plane;
import com.modelsRepos.PlaneRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planeRest")
public class PlaneController {

    @Autowired
    private PlaneRepo planeRepo;

    public PlaneController(PlaneRepo planeRepo) {
        this.planeRepo = planeRepo;
    }


    // get all plane
    @GetMapping
    public List<Plane> getAllPlanes() {
        return (List<Plane>) planeRepo.findAll();
    }

    // get planes data by id
    @GetMapping("/{id}")
    public Plane getPlaneById(@PathVariable long id) {
        return planeRepo.findById(id).orElse(null);
    }

    // create plane
    @PostMapping
    public Plane createPlane(@RequestBody Plane plane){
        return planeRepo.save(plane);
    }

    // update plane
    @PutMapping("/{id}")
    public Plane updatePlane(@RequestBody Plane plane,
                                           @PathVariable ("id") long id){
        Plane foundPlane = planeRepo.findById(id).orElseThrow(() -> new NotFoundException("Plane not found, id=" + id));
        foundPlane.setAirlines(plane.getAirlines());
        foundPlane.setInspectionDate(plane.getInspectionDate());
        foundPlane.setModel(plane.getModel());

        return planeRepo.save(foundPlane);
    }

    // delete plane
    @DeleteMapping("/{id}")
    public ResponseEntity<Plane> deletePlane(@PathVariable ("id") long id){
        Plane foundPlane = planeRepo.findById(id).orElseThrow(() -> new NotFoundException("Plane not found, id=" + id));
        planeRepo.delete(foundPlane);

        return ResponseEntity.ok().build();
    }

//    // ifPlaneInDatabase
//    @GetMapping("/ifPlaneInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}")
//    public Plane returnIfPlaneInDataBase(@PathVariable String country,
//         @PathVariable String city,
//         @PathVariable String postcode,
//         @PathVariable String street,
//         @PathVariable String buildingNr,
//         @PathVariable String apartmentNr
//    ){
//        List<Plane> foundPlanees = planeRepo.searchForPlane(
//                country, city, postcode, street, Integer.parseInt(buildingNr), Integer.parseInt(apartmentNr));
////                plane.getCountry(),
////                plane.getCity(),
////                plane.getPostcode(),
////                plane.getStreet(),
////                plane.getBuildingNr(),
////                plane.getApartmentNr());
//
//        if (foundPlanees.isEmpty()){
//            return null;
//        }else{
//            return foundPlanees.get(0);
//        }
//    }
}
