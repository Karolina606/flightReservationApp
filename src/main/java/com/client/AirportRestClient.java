package com.client;

import com.model.Airport;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportRestClient {

    private static final String GET_ALL_AIRPORT_API = "http://localhost:8080/airportRest";
    private static final String GET_AIRPORT_BY_ID_API = "http://localhost:8080/airportRest/{id}";
    private static final String CREATE_AIRPORT_API = "http://localhost:8080/airportRest";
    private static final String UPDATE_AIRPORT_API = "http://localhost:8080/airportRest/{id}";
    private static final String DELETE_AIRPORT_API = "http://localhost:8080/airportRest/{id}";
    //private static final String IF_AIRPORT_IN_DATABASE_API = "http://localhost:8080/airportRest/ifAirportInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<Airport> callGetAllAirportApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<Airport> airportList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<Airport> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, Airport.class);
        ResponseEntity<List<Airport>> result = restTemplate.exchange(GET_ALL_AIRPORT_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Airport>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static Airport callGetAirportByIdApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        Airport airport = restTemplate.getForObject(GET_AIRPORT_BY_ID_API, Airport.class, param);
        System.out.println(airport);
        return airport;
    }

    public static void callCreateAirportApi(Airport airport){
        ResponseEntity<Airport> airportResponse = restTemplate.postForEntity(CREATE_AIRPORT_API, airport, Airport.class);
        System.out.println("Dodano nowe lotnisko" + airportResponse.getBody());
    }

    public static void callUpdateAirportApi(Airport airport){
        Map<String, Long> param = new HashMap<>();
        param.put("id", airport.getId());

        restTemplate.put(UPDATE_AIRPORT_API, airport, param);
    }

    public static void callDeleteAirportApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_AIRPORT_API, param);
    }

//    public static Airport returnIfAirportInDataBase(Airport airport){
//        Map<String, String> param = new HashMap<>();
//        param.put("country", airport.getCountry());
//        param.put("city", airport.getCity());
//        param.put("postcode", airport.getPostcode());
//        param.put("street", airport.getStreet());
//        param.put("buildingNr",Integer.toString(airport.getBuildingNr()));
//        param.put("apartmentNr", Integer.toString(airport.getApartmentNr()));
//
//        return restTemplate.getForObject(IF_AIRPORT_IN_DATABASE_API, Airport.class, param);
//    }
}
