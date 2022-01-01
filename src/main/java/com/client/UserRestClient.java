package com.client;

import com.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRestClient {

    private static final String GET_ALL_USER_API = "http://localhost:8080/userRest";
    private static final String GET_USER_BY_ID_API = "http://localhost:8080/userRest/{login}";
    private static final String CREATE_USER_API = "http://localhost:8080/userRest";
    private static final String UPDATE_USER_API = "http://localhost:8080/userRest/{login}";
    private static final String DELETE_USER_API = "http://localhost:8080/userRest/{login}";
    //private static final String IF_USER_IN_DATABASE_API = "http://localhost:8080/userRest/ifUserInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<User> callGetAllUserApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<User> userList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<User> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, User.class);
        ResponseEntity<List<User>> result = restTemplate.exchange(GET_ALL_USER_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<User>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static User callGetUserByLoginApi(String login){
        Map<String, String> param = new HashMap<>();
        param.put("login", login);

        User user = restTemplate.getForObject(GET_USER_BY_ID_API, User.class, param);
        System.out.println(user);
        return user;
    }

    public static void callCreateUserApi(User user){
        ResponseEntity<User> userResponse = restTemplate.postForEntity(CREATE_USER_API, user, User.class);
        System.out.println("Dodano nowy samolot" + userResponse.getBody());
    }

    public static void callUpdateUserApi(User user){
        Map<String, String> param = new HashMap<>();
        param.put("login", user.getLogin());

        restTemplate.put(UPDATE_USER_API, user, param);
    }

    public static void callDeleteUserApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_USER_API, param);
    }

//    public static User returnIfUserInDataBase(User user){
//        Map<String, String> param = new HashMap<>();
//        param.put("country", user.getCountry());
//        param.put("city", user.getCity());
//        param.put("postcode", user.getPostcode());
//        param.put("street", user.getStreet());
//        param.put("buildingNr",Integer.toString(user.getBuildingNr()));
//        param.put("apartmentNr", Integer.toString(user.getApartmentNr()));
//
//        return restTemplate.getForObject(IF_USER_IN_DATABASE_API, User.class, param);
//    }
}
