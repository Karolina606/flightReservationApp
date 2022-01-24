package com.controller;


import com.model.Address;
import com.modelsRepos.AddressRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressRest")
public class AddressController {

    @Autowired
    private AddressRepo addressRepo;

    public AddressController(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    // get all address
    @GetMapping
    public List<Address> getAllAddresses() {
        return (List<Address>) addressRepo.findAll();
    }

    // get addresss data by id
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable long id) {
        return addressRepo.findById(id).orElse(null);
    }

    // create address
    @PostMapping
    public Address createAddress(@RequestBody Address address){
        if(!validateAddress(address)){
            return null;
        }
        return addressRepo.save(address);
    }

    // update address
    @PutMapping("/{id}")
    public Address updateAddress(@RequestBody Address address,
                                           @PathVariable ("id") long id){
        Address foundAddress = addressRepo.findById(id).orElseThrow(() -> new NotFoundException("Address not found, id=" + id));
        foundAddress.setCountry(address.getCountry());
        foundAddress.setCity(address.getCity());
        foundAddress.setStreet(address.getStreet());
        foundAddress.setPostcode(address.getPostcode());
        foundAddress.setBuildingNr(address.getBuildingNr());
        foundAddress.setApartmentNr(address.getApartmentNr());

        return addressRepo.save(foundAddress);
    }

    // delete address
    @DeleteMapping("/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable ("id") long id){
        Address foundAddress = addressRepo.findById(id).orElseThrow(() -> new NotFoundException("Address not found, id=" + id));
        addressRepo.delete(foundAddress);

        return ResponseEntity.ok().build();
    }

    // ifAddressInDatabase
    @GetMapping("/ifAddressInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}")
    public Address returnIfAddressInDataBase(@PathVariable String country,
         @PathVariable String city,
         @PathVariable String postcode,
         @PathVariable String street,
         @PathVariable String buildingNr,
         @PathVariable String apartmentNr
    ){
        List<Address> foundAddresses = addressRepo.searchForAddress(
                country, city, postcode, street, Integer.parseInt(buildingNr), Integer.parseInt(apartmentNr));
//                address.getCountry(),
//                address.getCity(),
//                address.getPostcode(),
//                address.getStreet(),
//                address.getBuildingNr(),
//                address.getApartmentNr());

        if (foundAddresses.isEmpty()){
            return null;
        }else{
            return foundAddresses.get(0);
        }
    }

    public static boolean validateAddress(Address address){
        // Sprawdzenie adresu
        if(address.getCity().isEmpty() || address.getCity().matches("^(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*\\_\\-\\=\\+]).*")){
            return false;
        }else if (address.getStreet().isEmpty() || address.getStreet().matches("^(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*\\_\\-\\=\\+]).*")){
            return false;
        }else if(address.getCountry().isEmpty() || address.getCountry().matches("^(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*\\_\\-\\=\\+]).*")){
            return false;
        }else if(address.getPostcode().isEmpty() || !address.getPostcode().matches("[0-9]{2}-[0-9]{3}")){
            return false;
        }
        return true;
    }
}
