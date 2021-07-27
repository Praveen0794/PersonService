package com.person.service.poc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.person.service.poc.dto.Person;

public class SpringRestClient {

	private static final String GET_PERSONS_ENDPOINT_URL = "http://localhost:8080/api/v1/persons";
	private static final String GET_PERSON_ENDPOINT_URL = "http://localhost:8080/api/v1/persons/{id}";
	private static final String CREATE_PERSON_ENDPOINT_URL = "http://localhost:8080/api/v1/persons";
	private static final String UPDATE_PERSON_ENDPOINT_URL = "http://localhost:8080/api/v1/persons/{id}";
	private static final String DELETE_PERSON_ENDPOINT_URL = "http://localhost:8080/api/v1/persons/{id}";
	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringRestClient springRestClient = new SpringRestClient();

		// Step1: first create a new person
		springRestClient.createperson();

		// Step 2: get new created person from step1
		springRestClient.getpersonById();

		// Step3: get all persons
		springRestClient.getpersons();

		// Step4: Update person with id = 1
		springRestClient.updateperson();

		// Step5: Delete person with id = 1
		springRestClient.deleteperson();
	}

	private void getpersons() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_PERSONS_ENDPOINT_URL, HttpMethod.GET, entity,
				String.class);

		System.out.println(result);
	}

	private void getpersonById() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");

		RestTemplate restTemplate = new RestTemplate();
		Person result = restTemplate.getForObject(GET_PERSON_ENDPOINT_URL, Person.class, params);

		System.out.println(result);
	}

	private void createperson() {

		Person newPerson = new Person("admin", "admin");

		RestTemplate restTemplate = new RestTemplate();
		Person result = restTemplate.postForObject(CREATE_PERSON_ENDPOINT_URL, newPerson, Person.class);

		System.out.println(result);
	}

	private void updateperson() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		Person updatedPerson = new Person("admin123", "admin123");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(UPDATE_PERSON_ENDPOINT_URL, updatedPerson, params);
	}

	private void deleteperson() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(DELETE_PERSON_ENDPOINT_URL, params);
	}
}
