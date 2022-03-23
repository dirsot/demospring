package com.example.demo.repo;

import com.example.demo.Person;
import org.springframework.data.repository.CrudRepository;

//@Transactional
public interface PersonRepository extends CrudRepository<Person, Long> {
}
