package com.client;

import com.model.Employee;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class EmployeeRestClient {

    private static final String GET_ALL_EMPLOYEE_API = "http://localhost:8080/employeeRest";
    private static final String GET_EMPLOYEE_BY_ID_API = "http://localhost:8080/employeeRest/{id}";
    private static final String GET_EMPLOYEE_WITH_ROLE_API = "http://localhost:8080/employeeRest/role/{role}";
    private static final String CREATE_EMPLOYEE_API = "http://localhost:8080/employeeRest";
    private static final String ADD_EMPLOYEE_TO_FLIGHT_CREW_API = "http://localhost:8080/employeeRest/addEmployeeToFlightCrew/{flightId}";
    private static final String UPDATE_EMPLOYEE_API = "http://localhost:8080/employeeRest/{id}";
    private static final String DELETE_EMPLOYEE_API = "http://localhost:8080/employeeRest/{id}";


    static RestTemplate restTemplate = new RestTemplate();

    public static List<Employee> callGetAllEmployeeApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<List<Employee>> result = restTemplate.exchange(GET_ALL_EMPLOYEE_API, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Employee>>() {});
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static Employee callGetEmployeeByIdApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        Employee employee = restTemplate.getForObject(GET_EMPLOYEE_BY_ID_API, Employee.class, param);
        System.out.println(employee);
        return employee;
    }

    public static List<Employee> callGetEmployeeWithRoleApi(int role){
        Map<String, Integer> param = new HashMap<>();
        param.put("role", role);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<List<Employee>> result = restTemplate.exchange(GET_EMPLOYEE_WITH_ROLE_API, HttpMethod.GET,
                entity, new ParameterizedTypeReference<List<Employee>>() {}, param);
        System.out.println(result.getBody());
        return result.getBody();
    }

    public static boolean callCreateEmployeeApi(Employee employee){
        ResponseEntity<Employee> employeeResponse = restTemplate.postForEntity(CREATE_EMPLOYEE_API, employee, Employee.class);

        if(employeeResponse.getBody() == null){
            return false;
        }else{
            System.out.println("Dodano pracownika " + employeeResponse.getBody());
            return true;
        }
    }

    public static boolean callAddEmployeeToFlightCrew(Employee employee, Long flightId){
        Map<String, Long> param = new HashMap<>();
        param.put("flightId", flightId);

        System.out.println(employee.toString());
        System.out.println(flightId);

        ResponseEntity<Employee> employeeResponse = restTemplate.postForEntity(ADD_EMPLOYEE_TO_FLIGHT_CREW_API, employee, Employee.class, param);
        if(employeeResponse.getBody() == null){
            return false;
        }else{
            System.out.println("Dodano pracownika do lotu" + employeeResponse.getBody());
            return true;
        }
    }

    public static void callUpdateEmployeeApi(Employee employee){
        Map<String, Long> param = new HashMap<>();
        param.put("id", employee.getId());

        restTemplate.put(UPDATE_EMPLOYEE_API, employee, param);
    }

    public static void callDeleteEmployeeApi(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);

        restTemplate.delete(DELETE_EMPLOYEE_API, param);
    }

}
