package com.person.service.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.person.service.poc.dto.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
