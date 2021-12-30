package com.client;

import com.model.Plane;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaneRestClient {

    private static final String GET_ALL_PLANE_API = "http://localhost:8080/planeRest";
    private static final String GET_PLANE_BY_ID_API = "http://localhost:8080/planeRest/{id}";
    private static final String CREATE_PLANE_API = "http://localhost:8080/planeRest";
    private static final String UPDATE_PLANE_API = "http://localhost:8080/planeRest/{id}";
    private static final String DELETE_PLANE_API = "http://localhost:8080/planeRest/{id}";
    //private static final String IF_PLANE_IN_DATABASE_API = "http://localhost:8080/planeRest/ifPlaneInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<Plane> callGetAllPlaneApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<Plane> planeList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<Plane> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, Plane.class);
        ResponseEntity<List<Plane>> result = restTemplate.exchange(GET_ALL_PLANE_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Plane>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static Plane callGetPlaneByIdApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        Plane plane = restTemplate.getForObject(GET_PLANE_BY_ID_API, Plane.class, param);
        System.out.println(plane);
        return plane;
    }

    public static void callCreatePlaneApi(Plane plane){
        ResponseEntity<Plane> planeResponse = restTemplate.postForEntity(CREATE_PLANE_API, plane, Plane.class);
        System.out.println("Dodano nowy samolot" + planeResponse.getBody());
    }

    public static void callUpdatePlaneApi(Plane plane){
        Map<String, Long> param = new HashMap<>();
        param.put("id", plane.getId());

        restTemplate.put(UPDATE_PLANE_API, plane, param);
    }

    public static void callDeletePlaneApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_PLANE_API, param);
    }

//    public static Plane returnIfPlaneInDataBase(Plane plane){
//        Map<String, String> param = new HashMap<>();
//        param.put("country", plane.getCountry());
//        param.put("city", plane.getCity());
//        param.put("postcode", plane.getPostcode());
//        param.put("street", plane.getStreet());
//        param.put("buildingNr",Integer.toString(plane.getBuildingNr()));
//        param.put("apartmentNr", Integer.toString(plane.getApartmentNr()));
//
//        return restTemplate.getForObject(IF_PLANE_IN_DATABASE_API, Plane.class, param);
//    }
}
