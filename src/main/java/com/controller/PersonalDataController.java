package com.controller;


import com.model.PersonalData;
import com.modelsRepos.AddressRepo;
import com.modelsRepos.PersonalDataRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@Component
@RequestMapping("/personalDataRest")
public class PersonalDataController {
    @Autowired
    private PersonalDataRepo personalDataRepo;
    @Autowired
    private AddressRepo addressRepo;

    public PersonalDataController(PersonalDataRepo personalDataRepo, AddressRepo addressRepo) {
        this.personalDataRepo = personalDataRepo;
        this.addressRepo = addressRepo;
    }

    // get all personal data
    @GetMapping
    public List<PersonalData> getAllPersonalData() {
        return (List<PersonalData>) personalDataRepo.findAll();
    }

    // get personal data by id
    @GetMapping("/{pesel}")
    public PersonalData getPersonalDatabyId(@PathVariable long pesel) {
        return personalDataRepo.findById(pesel).orElse(null);
    }

    // create personal data
    @PostMapping
    @Transactional
    public PersonalData createPersonalData(@RequestBody PersonalData personalData){

        // Sprawdzenie i zapisanie adresu
        if(!AddressController.validateAddress(personalData.getAddress())){
            return null;
        }
        addressRepo.save(personalData.getAddress());

        // Sprawdzanie danych personalnych
        if(!validatePersonalData(personalData)){
            return null;
        }
        return personalDataRepo.save(personalData);
    }

    // update personal data
    @PutMapping("/{pesel}")
    public PersonalData updatePersonalData(@RequestBody PersonalData personalData,
                                           @PathVariable ("pesel") long pesel){
        PersonalData foundPersonalData = personalDataRepo.findById(pesel).orElseThrow(() -> new NotFoundException("Personal data not found, pesel=" + pesel));
        foundPersonalData.setFirstName(personalData.getFirstName());
        foundPersonalData.setLastName(personalData.getLastName());
        foundPersonalData.setAddress(personalData.getAddress());
        foundPersonalData.setDateOfBirth(personalData.getDateOfBirth());
        foundPersonalData.setPhoneNumber(personalData.getPhoneNumber());

        return personalDataRepo.save(foundPersonalData);
    }

    // delete personal data
    @DeleteMapping("/{pesel}")
    public ResponseEntity<PersonalData> deletePersonalData(@PathVariable ("pesel") long pesel){
        PersonalData foundPersonalData = personalDataRepo.findById(pesel).orElseThrow(() -> new NotFoundException("Personal data not found, pesel=" + pesel));
        personalDataRepo.delete(foundPersonalData);

        return ResponseEntity.ok().build();
    }


    public static boolean validatePersonalData(PersonalData personalData){
        // Sprawdzenie danych
        if(personalData.getFirstName().isEmpty() || personalData.getFirstName().matches("^(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*\\_\\-\\=\\+]).*")){
            return false;
        }else if (personalData.getLastName().isEmpty() || personalData.getLastName().matches("^(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*\\_\\-\\=\\+]).*")){
            return false;
        }else if(personalData.getPesel().toString().length() != 11){
            return false;
        }else if(personalData.getPhoneNumber().toString().length() != 9){
            return false;
        }
        return true;
    }
}
