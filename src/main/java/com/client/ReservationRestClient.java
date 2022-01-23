package com.client;

import com.model.Employee;
import com.model.Reservation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRestClient {

    private static final String GET_ALL_RESERVATION_API = "http://localhost:8080/reservationRest";
    private static final String GET_RESERVATION_BY_ID_API = "http://localhost:8080/reservationRest/{id}";
    private static final String GET_RESERVATION_BY_PESEL_API = "http://localhost:8080/reservationRest/getReservarionByPesel/{pesel}";
    private static final String CREATE_RESERVATION_API = "http://localhost:8080/reservationRest";
    private static final String CREATE_MULTIPLE_RESERVATIONS_API = "http://localhost:8080/reservationRest/createMultipleReservations";
    private static final String UPDATE_RESERVATION_API = "http://localhost:8080/reservationRest/{id}";
    private static final String DELETE_RESERVATION_API = "http://localhost:8080/reservationRest/{id}";
    //private static final String IF_RESERVATION_IN_DATABASE_API = "http://localhost:8080/reservationRest/ifReservationInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<Reservation> callGetAllReservationApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<Reservation> reservationList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<Reservation> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, Reservation.class);
        ResponseEntity<List<Reservation>> result = restTemplate.exchange(GET_ALL_RESERVATION_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Reservation>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static Reservation callGetReservationByIdApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        Reservation reservation = restTemplate.getForObject(GET_RESERVATION_BY_ID_API, Reservation.class, param);
        System.out.println(reservation);
        return reservation;
    }

    public static List<Reservation> callGetReservationByPeselApi(Long pesel){
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        String urlTemplate = UriComponentsBuilder.fromHttpUrl(GET_RESERVATION_BY_PESEL_API)
//                .queryParam("pesel", "{pesel}")
//                .encode()
//                .toUriString();
//
//        Map<String, Long> param = new HashMap<>();
//        param.put("pesel", pesel);
//
//        //List<Reservation> reservations = (List<Reservation>) restTemplate.getForObject(GET_RESERVATION_BY_PESEL_API, Reservation.class, param);
//        ResponseEntity<List<Reservation>> response = restTemplate.exchange(
//                urlTemplate,
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<List<Reservation>>() {},
//                param
//        );

        Map<String, Long> param = new HashMap<>();
        param.put("pesel", pesel);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<List<Reservation>> result = restTemplate.exchange(GET_RESERVATION_BY_PESEL_API, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Reservation>>() {}, param);
        return result.getBody();
    }

    public static void callCreateReservationApi(Reservation reservation){
        ResponseEntity<Reservation> reservationResponse = restTemplate.postForEntity(CREATE_RESERVATION_API, reservation, Reservation.class);

        if (reservationResponse.getBody() != null){
            System.out.println("Dodano nową rezerwację" + reservationResponse.getBody());
        }else{
            System.out.println("Niestety nie ma już miejsc na ten lot lub osoba jest za mloda");
        }
    }

    public static void callCreateMultipleReservationsApi(List<Reservation> reservations){
        ResponseEntity<Boolean> reservationsResponse = restTemplate.postForEntity(CREATE_MULTIPLE_RESERVATIONS_API, reservations, Boolean.class);

        if (Boolean.TRUE.equals(reservationsResponse.getBody())){
            System.out.println("Dodano nowe rezerwacje" + reservationsResponse.getBody());
        }else{
            System.out.println("Niestety nie ma już miejsc na ten lot lub nie ma osoby doroslej wsrod rezerwacji");
        }
    }

    public static void callUpdateReservationApi(Reservation reservation){
        Map<String, Long> param = new HashMap<>();
        param.put("id", reservation.getId());

        restTemplate.put(UPDATE_RESERVATION_API, reservation, param);
    }

    public static void callDeleteReservationApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_RESERVATION_API, param);
    }

//    public static Reservation returnIfReservationInDataBase(Reservation reservation){
//        Map<String, String> param = new HashMap<>();
//        param.put("country", reservation.getCountry());
//        param.put("city", reservation.getCity());
//        param.put("postcode", reservation.getPostcode());
//        param.put("street", reservation.getStreet());
//        param.put("buildingNr",Integer.toString(reservation.getBuildingNr()));
//        param.put("apartmentNr", Integer.toString(reservation.getApartmentNr()));
//
//        return restTemplate.getForObject(IF_RESERVATION_IN_DATABASE_API, Reservation.class, param);
//    }
}
