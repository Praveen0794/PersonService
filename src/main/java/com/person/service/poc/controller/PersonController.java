package com.person.service.poc.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.person.service.poc.dto.Person;
import com.person.service.poc.exception.ResourceNotFoundException;
import com.person.service.poc.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class PersonController {
	@Autowired
	private PersonRepository personRepository;

	@GetMapping("/persons")
	public List<Person> getAllEmployees() {
		return personRepository.findAll();
	}

	@GetMapping("/persons/count")
	public int getAllEmployeesCount() {
		return personRepository.findAll().size();
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Person person = personRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(person);
	}

	@PostMapping("/persons")
	public Person createEmployee(@Valid @RequestBody Person person) {
		return personRepository.save(person);
	}

	@PutMapping("/persons/{id}")
	public ResponseEntity<Person> updateEmployee(@PathVariable(value = "id") Long employeeId,
												 @Valid @RequestBody Person personDetails) throws ResourceNotFoundException {
		Person person = personRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + employeeId));

		person.setSurname(personDetails.getSurname());
		person.setFirstName(personDetails.getFirstName());
		final Person updatedPerson = personRepository.save(person);
		return ResponseEntity.ok(updatedPerson);
	}

	@DeleteMapping("/persons/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Person person = personRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + employeeId));

		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
