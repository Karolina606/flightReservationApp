package com.client;

import com.model.PlaneModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaneModelRestClient {

    private static final String GET_ALL_PLANE_MODEL_API = "http://localhost:8080/planeModelRest";
    private static final String GET_PLANE_MODEL_BY_ID_API = "http://localhost:8080/planeModelRest/{id}";
    private static final String CREATE_PLANE_MODEL_API = "http://localhost:8080/planeModelRest";
    private static final String UPDATE_PLANE_MODEL_API = "http://localhost:8080/planeModelRest/{id}";
    private static final String DELETE_PLANE_MODEL_API = "http://localhost:8080/planeModelRest/{id}";
    //private static final String IF_PLANE_MODEL_IN_DATABASE_API = "http://localhost:8080/planeModelRest/ifPlaneModelInDatabase/{country}/{city}/{postcode}/{street}/{buildingNr}/{apartmentNr}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<PlaneModel> callGetAllPlaneModelApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<PlaneModel> planeModelList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<PlaneModel> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, PlaneModel.class);
        ResponseEntity<List<PlaneModel>> result = restTemplate.exchange(GET_ALL_PLANE_MODEL_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<PlaneModel>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static PlaneModel callGetPlaneModelByIdApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        PlaneModel planeModel = restTemplate.getForObject(GET_PLANE_MODEL_BY_ID_API, PlaneModel.class, param);
        System.out.println(planeModel);
        return planeModel;
    }

    public static void callCreatePlaneModelApi(PlaneModel planeModel){
        ResponseEntity<PlaneModel> planeModelResponse = restTemplate.postForEntity(CREATE_PLANE_MODEL_API, planeModel, PlaneModel.class);
        System.out.println("Dodano nowy samolot" + planeModelResponse.getBody());
    }

    public static void callUpdatePlaneModelApi(PlaneModel planeModel){
        Map<String, Long> param = new HashMap<>();
        param.put("id", planeModel.getId());

        restTemplate.put(UPDATE_PLANE_MODEL_API, planeModel, param);
    }

    public static void callDeletePlaneModelApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_PLANE_MODEL_API, param);
    }

//    public static PlaneModel returnIfPlaneModelInDataBase(PlaneModel planeModel){
//        Map<String, String> param = new HashMap<>();
//        param.put("country", planeModel.getCountry());
//        param.put("city", planeModel.getCity());
//        param.put("postcode", planeModel.getPostcode());
//        param.put("street", planeModel.getStreet());
//        param.put("buildingNr",Integer.toString(planeModel.getBuildingNr()));
//        param.put("apartmentNr", Integer.toString(planeModel.getApartmentNr()));
//
//        return restTemplate.getForObject(IF_PLANE_MODEL_IN_DATABASE_API, PlaneModel.class, param);
//    }
}
