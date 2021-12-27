//package com.controller;
//
//
//import com.model.PersonalData;
//import com.model.PersonalDataRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collection;
//
//@RestController
//public class PersonalDataController {
//    @Autowired
//    private PersonalDataRepo repo;
//
//    @RequestMapping(method = RequestMethod.GET)
//    public Collection<PersonalData> getPersonalData() {
//        return (Collection<PersonalData>) repo.findAll();
//    }
//
////    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
////    public PersonalData getPersonalData(@PathVariable long id) {
////        return repo.findOne(id);
////    }
//}
