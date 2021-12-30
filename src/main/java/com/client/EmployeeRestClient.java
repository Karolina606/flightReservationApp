package com.client;

import com.model.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRestClient {

    private static final String GET_ALL_EMPLOYEE_API = "http://localhost:8080/employeeRest";
    private static final String GET_EMPLOYEE_BY_ID_API = "http://localhost:8080/employeeRest/{id}";
    private static final String CREATE_EMPLOYEE_API = "http://localhost:8080/employeeRest";
    private static final String UPDATE_EMPLOYEE_API = "http://localhost:8080/employeeRest/{id}";
    private static final String DELETE_EMPLOYEE_API = "http://localhost:8080/employeeRest/{id}";


    static RestTemplate restTemplate = new RestTemplate();
    public static void main(String[] args){
    }

    public static List<Employee> callGetAllEmployeeApi(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // List<Employee> employeeList = restTemplate.getForObject(GET_ALL_PERSONAL_DATA_API, List.class);
        // ResponseEntity<Employee> result = restTemplate.exchange(GET_ALL_PERSONAL_DATA_API, HttpMethod.GET, entity, Employee.class);
        ResponseEntity<List<Employee>> result = restTemplate.exchange(GET_ALL_EMPLOYEE_API, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Employee>>() {});
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

    public static void callCreateEmployeeApi(Employee employee){
        ResponseEntity<Employee> employeeResponse = restTemplate.postForEntity(CREATE_EMPLOYEE_API, employee, Employee.class);
        System.out.println("Dodano dane osobowe " + employeeResponse.getBody());
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
