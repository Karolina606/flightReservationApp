package com.controller;

import com.model.Address;
import com.modelsRepos.AddressRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepo addressRepo;

    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public List<Address> findAllAddresses(String filterText){
            return (List<Address>) addressRepo.findAll();
    }

    public long countUsers(){
        return addressRepo.count();
    }

    public void deleteUsers(Address address){
        addressRepo.delete(address);
    }

    public void saveUser(Address address){
        if(address == null){
            System.err.println("User is null");
        }
        addressRepo.save(address);
    }
}
