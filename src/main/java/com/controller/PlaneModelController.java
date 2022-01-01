package com.controller;


import com.model.PlaneModel;
import com.modelsRepos.PlaneModelRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
@RequestMapping("/planeModelRest")
public class PlaneModelController {

    @Autowired
    private PlaneModelRepo planeModelRepo;

    public PlaneModelController(PlaneModelRepo planeModelRepo) {
        this.planeModelRepo = planeModelRepo;
    }
    
    // get all planeModel
    @GetMapping
    public List<PlaneModel> getAllPlaneModels() {
        return (List<PlaneModel>) planeModelRepo.findAll();
    }

    // get planeModels data by id
    @GetMapping("/{id}")
    public PlaneModel getPlaneModelById(@PathVariable long id) {
        return planeModelRepo.findById(id).orElse(null);
    }

    // create planeModel
    @PostMapping
    public PlaneModel createPlaneModel(@RequestBody PlaneModel planeModel){
        return planeModelRepo.save(planeModel);
    }

    // update planeModel
    @PutMapping("/{id}")
    public PlaneModel updatePlaneModel(@RequestBody PlaneModel planeModel,
                                           @PathVariable ("id") long id){
        PlaneModel foundPlaneModel = planeModelRepo.findById(id).orElseThrow(() -> new NotFoundException("PlaneModel not found, id=" + id));
        foundPlaneModel.setBrand(planeModel.getBrand());
        foundPlaneModel.setModelName(planeModel.getModelName());
        foundPlaneModel.setNumberOfFlightAttendants(planeModel.getNumberOfFlightAttendants());
        foundPlaneModel.setNumberOfPilots(planeModel.getNumberOfPilots());
        foundPlaneModel.setNumberOfSeats(planeModel.getNumberOfSeats());
        foundPlaneModel.setTankCapacity(planeModel.getTankCapacity());

        return planeModelRepo.save(foundPlaneModel);
    }

    // delete planeModel
    @DeleteMapping("/{id}")
    public ResponseEntity<PlaneModel> deletePlaneModel(@PathVariable ("id") long id){
        PlaneModel foundPlaneModel = planeModelRepo.findById(id).orElseThrow(() -> new NotFoundException("PlaneModel not found, id=" + id));
        planeModelRepo.delete(foundPlaneModel);

        return ResponseEntity.ok().build();
    }

//    // ifPlaneModelInDatabase
//    @GetMapping("/ifPlaneModelInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}")
//    public PlaneModel returnIfPlaneModelInDataBase(@PathVariable String country,
//         @PathVariable String city,
//         @PathVariable String postcode,
//         @PathVariable String street,
//         @PathVariable String buildingNr,
//         @PathVariable String apartmentNr
//    ){
//        List<PlaneModel> foundPlaneModels = planeModelRepo.searchForPlaneModel(
//                country, city, postcode, street, Integer.parseInt(buildingNr), Integer.parseInt(apartmentNr));
////                planeModel.getCountry(),
////                planeModel.getCity(),
////                planeModel.getPostcode(),
////                planeModel.getStreet(),
////                planeModel.getBuildingNr(),
////                planeModel.getApartmentNr());
//
//        if (foundPlaneModels.isEmpty()){
//            return null;
//        }else{
//            return foundPlaneModels.get(0);
//        }
//    }
}
