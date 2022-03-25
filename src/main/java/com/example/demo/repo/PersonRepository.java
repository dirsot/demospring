package com.example.demo.repo;

import com.example.demo.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    @Query("SELECT p FROM Person p WHERE p.id = :id")
    Person findPersonQuery(@Param("id") Long id);
}
