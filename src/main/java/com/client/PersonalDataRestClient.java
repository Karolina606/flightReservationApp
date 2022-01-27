package com.client;

import cn.licoy.encryptbody.annotation.decrypt.AESDecryptBody;
import com.model.PersonalData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class PersonalDataRestClient {

    private static final String GET_ALL_PERSONAL_DATA_API = "http://localhost:8080/personalDataRest";
    private static final String GET_PERSONAL_DATA_BY_ID_API = "http://localhost:8080/personalDataRest/{pesel}";
    private static final String CREATE_PERSONAL_DATA_API = "http://localhost:8080/personalDataRest";
    private static final String UPDATE_PERSONAL_DATA_API = "http://localhost:8080/personalDataRest/{pesel}";
    private static final String DELETE_PERSONAL_DATA_API = "http://localhost:8080/personalDataRest/{pesel}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<PersonalData> callGetAllPersonalDataApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<PersonalData> personalDataList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<PersonalData> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, PersonalData.class);
        ResponseEntity<List<PersonalData>> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<PersonalData>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static PersonalData callGetPersonalDataByIdApi(Long pesel){
        Map<String, Long> param = new HashMap<>();
        param.put("pesel", pesel);

        PersonalData personalData = restTemplate.getForObject(GET_PERSONAL_DATA_BY_ID_API, PersonalData.class, param);
        System.out.println(personalData);
        return personalData;
    }

    public static boolean callCreatePersonalDataApi(PersonalData personalData){
        ResponseEntity<PersonalData> personaDataResponse = restTemplate.postForEntity(CREATE_PERSONAL_DATA_API, personalData, PersonalData.class);

        if(personaDataResponse.getBody() == null){
            return false;
        }
        else{
            System.out.println("Dodano dane osobowe " + personaDataResponse.getBody());
            return true;
        }
    }

    public static void callUpdatePersonalDataApi(PersonalData personalData){
        Map<String, Long> param = new HashMap<>();
        param.put("pesel", personalData.getPesel());

        restTemplate.put(UPDATE_PERSONAL_DATA_API, personalData, param);
    }

    public static void callDeletePersonalDataApi(Long pesel){
        Map<String, Long> param = new HashMap<>();
        param.put("pesel", pesel);

        restTemplate.delete(DELETE_PERSONAL_DATA_API, param);
    }

}
