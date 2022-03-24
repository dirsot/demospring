package com.example.demo.repo;

import com.example.demo.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//@Transactional
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.id = :id")
    Person findPersonQuery(@Param("id") Long id);
}
