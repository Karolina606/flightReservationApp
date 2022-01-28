package com.client;

import com.model.Flight;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightRestClient {

    private static final String GET_ALL_FLIGHT_API = "http://localhost:8080/flightRest";
    private static final String GET_FLIGHT_BY_ID_API = "http://localhost:8080/flightRest/{id}";
    private static final String CREATE_FLIGHT_API = "http://localhost:8080/flightRest";
    private static final String UPDATE_FLIGHT_API = "http://localhost:8080/flightRest/{id}";
    private static final String DELETE_FLIGHT_API = "http://localhost:8080/flightRest/{id}";
    private static final String GET_OCCUPIED_SEATS_API = "http://localhost:8080/flightRest/occupiedSeats/{id}";
    //private static final String IF_FLIGHT_IN_DATABASE_API = "http://localhost:8080/flightRest/ifFlightInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<Flight> callGetAllFlightApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<Flight> flightList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<Flight> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, Flight.class);
        ResponseEntity<List<Flight>> result = restTemplate.exchange(GET_ALL_FLIGHT_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Flight>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static Flight callGetFlightByIdApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        Flight flight = restTemplate.getForObject(GET_FLIGHT_BY_ID_API, Flight.class, param);
        System.out.println(flight);
        return flight;
    }

    public static boolean callCreateFlightApi(Flight flight){
        ResponseEntity<Flight> flightResponse = restTemplate.postForEntity(CREATE_FLIGHT_API, flight, Flight.class);
         if(flightResponse.getBody() == null){
             return false;
         }

         System.out.println("Dodano nowy lot" + flightResponse.getBody());
         return true;
    }

    public static void callUpdateFlightApi(Flight flight){
        Map<String, Long> param = new HashMap<>();
        param.put("id", flight.getId());

        restTemplate.put(UPDATE_FLIGHT_API, flight, param);
    }

    public static void callDeleteFlightApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_FLIGHT_API, param);
    }

    public static Integer callGetOccupiedSeats(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        Integer occupiedSeats = restTemplate.getForObject(GET_OCCUPIED_SEATS_API, Integer.class, param);
        return occupiedSeats;
    }

//    public static Flight returnIfFlightInDataBase(Flight flight){
//        Map<String, String> param = new HashMap<>();
//        param.put("country", flight.getCountry());
//        param.put("city", flight.getCity());
//        param.put("postcode", flight.getPostcode());
//        param.put("street", flight.getStreet());
//        param.put("buildingNr",Integer.toString(flight.getBuildingNr()));
//        param.put("apartmentNr", Integer.toString(flight.getApartmentNr()));
//
//        return restTemplate.getForObject(IF_FLIGHT_IN_DATABASE_API, Flight.class, param);
//    }
}
