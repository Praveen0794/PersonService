package com.person.service.poc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.person.service.poc.dto.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonServiceControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllPersons() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/persons",
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetPersonById() {
		Person person = restTemplate.getForObject(getRootUrl() + "/persons/1", Person.class);
		System.out.println(person.getFirstName());
		assertNotNull(person);
	}

	@Test
	public void testCreatePerson() {
		Person person = new Person();
		person.setFirstName("admin");
		person.setSurname("admin");

		ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/persons", person, Person.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePerson() {
		int id = 1;
		Person person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
		person.setFirstName("admin1");
		person.setSurname("admin2");

		restTemplate.put(getRootUrl() + "/persons/" + id, person);

		Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
		assertNotNull(updatedPerson);
	}

	@Test
	public void testDeletePerson() {
		int id = 2;
		Person person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
		assertNotNull(person);

		restTemplate.delete(getRootUrl() + "/persons/" + id);

		try {
			person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
