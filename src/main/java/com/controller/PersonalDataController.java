package com.controller;


import com.model.PersonalData;
import com.modelsRepos.PersonalDataRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personalDataRest")
public class PersonalDataController {
    @Autowired
    private PersonalDataRepo personalDataRepo;

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
    public PersonalData createPersonalData(@RequestBody PersonalData personalData){
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
}
