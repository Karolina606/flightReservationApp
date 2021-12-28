package com.controller;

import com.model.Address;
import com.model.AddressRepo;
import com.model.PersonalData;
import com.model.PersonalDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalDataService {

    private PersonalDataRepo personalDataRepo;
    private AddressRepo addressRepo;

    @Autowired
    public PersonalDataService(PersonalDataRepo personalDataRepo, AddressRepo addresRepo){
        this.personalDataRepo = personalDataRepo;
        this.addressRepo = addresRepo;
    }

    public List<PersonalData> findAllData(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return (List<PersonalData>) personalDataRepo.findAll();
        }else{
            return personalDataRepo.search(filterText);
        }
    }

    public long countPersonalData(){
        return  personalDataRepo.count();
    }

    public void deletePersonalData(PersonalData personalData){
             personalDataRepo.delete(personalData);
    }

    public void savePersonalData(PersonalData personalData){
        if(personalData == null){
            System.err.println("PersonalData is null");
        }else{
            personalDataRepo.save(personalData);
        }
    }

    public Address findAddressById(long id) {
//        List<Long> idList = new ArrayList<>();
//        idList.add(id);
        Optional<Address> result = addressRepo.findById(id);
        return result.orElse(null);
    }
}
