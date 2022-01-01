package com.controller;


import com.model.User;
import com.modelsRepos.UserRepo;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userRest")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    // get all user
    @GetMapping
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    // get users data by id
    @GetMapping("/{login}")
    public User getUserById(@PathVariable ("login") String login) {
        return userRepo.findById(login).orElse(null);
    }

    // create user
    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepo.save(user);
    }

    // update user
    @PutMapping("/{login}")
    public User updateUser(@RequestBody User user,
                                           @PathVariable ("login") String login){
        User foundUser = userRepo.findById(login).orElseThrow(() -> new NotFoundException("User not found, login=" + login));
        foundUser.setPersonalData(user.getPersonalData());
        foundUser.setPasswordHash(user.getPasswordHash());
        foundUser.setRole(user.getRole());

        return userRepo.save(foundUser);
    }

    // delete user
    @DeleteMapping("/{login}")
    public ResponseEntity<User> deleteUser(@PathVariable ("login") String login){
        User foundUser = userRepo.findById(login).orElseThrow(() -> new NotFoundException("User not found, login=" + login));
        userRepo.delete(foundUser);

        return ResponseEntity.ok().build();
    }

//    // ifUserInDatabase
//    @GetMapping("/ifUserInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}")
//    public User returnIfUserInDataBase(@PathVariable String country,
//         @PathVariable String city,
//         @PathVariable String postcode,
//         @PathVariable String street,
//         @PathVariable String buildingNr,
//         @PathVariable String apartmentNr
//    ){
//        List<User> foundUseres = userRepo.searchForUser(
//                country, city, postcode, street, Integer.parseInt(buildingNr), Integer.parseInt(apartmentNr));
////                user.getCountry(),
////                user.getCity(),
////                user.getPostcode(),
////                user.getStreet(),
////                user.getBuildingNr(),
////                user.getApartmentNr());
//
//        if (foundUseres.isEmpty()){
//            return null;
//        }else{
//            return foundUseres.get(0);
//        }
//    }
}
