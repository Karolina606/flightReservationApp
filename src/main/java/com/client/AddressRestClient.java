package com.client;

import com.controller.AddressController;
import com.model.Address;
import com.modelsRepos.AddressRepo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressRestClient {

    private static final String GET_ALL_ADDRESS_API = "http://localhost:8080/addressRest";
    private static final String GET_ADDRESS_BY_ID_API = "http://localhost:8080/addressRest/{id}";
    private static final String CREATE_ADDRESS_API = "http://localhost:8080/addressRest";
    private static final String UPDATE_ADDRESS_API = "http://localhost:8080/addressRest/{id}";
    private static final String DELETE_ADDRESS_API = "http://localhost:8080/addressRest/{id}";
    private static final String IF_ADDRESS_IN_DATABASE_API = "http://localhost:8080/addressRest/ifAddressInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<Address> callGetAllAddressApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<Address> addressList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<Address> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, Address.class);
        ResponseEntity<List<Address>> result = restTemplate.exchange(GET_ALL_ADDRESS_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Address>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static Address callGetAddressByIdApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        Address address = restTemplate.getForObject(GET_ADDRESS_BY_ID_API, Address.class, param);
        System.out.println(address);
        return address;
    }

    public static void callCreateAddressApi(Address address){
        ResponseEntity<Address> addressResponse = restTemplate.postForEntity(CREATE_ADDRESS_API, address, Address.class);
        System.out.println("Dodano nowy adres" + addressResponse.getBody());
    }

    public static void callUpdateAddressApi(Address address){
        Map<String, Long> param = new HashMap<>();
        param.put("id", address.getId());

        restTemplate.put(UPDATE_ADDRESS_API, address, param);
    }

    public static void callDeleteAddressApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_ADDRESS_API, param);
    }

    public static Address returnIfAddressInDataBase(Address address){
        Map<String, String> param = new HashMap<>();
        param.put("country", address.getCountry());
        param.put("city", address.getCity());
        param.put("postcode", address.getPostcode());
        param.put("street", address.getStreet());
        param.put("buildingNr",Integer.toString(address.getBuildingNr()));
        param.put("apartmentNr", Integer.toString(address.getApartmentNr()));

        return restTemplate.getForObject(IF_ADDRESS_IN_DATABASE_API, Address.class, param);
    }
}
