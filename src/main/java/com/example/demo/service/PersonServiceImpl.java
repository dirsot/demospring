package com.example.demo.service;

import com.example.demo.Person;
import com.example.demo.repo.PersonRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service("personService")
public class PersonServiceImpl implements PersonService {
    private final Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Person getPerson(long id) {
        Session session = entityManager.unwrap(Session.class);
        logger.info("1 call" + session.getSessionFactory().getStatistics());
        Person person = personRepository.findById(id).orElseThrow();
        logger.info("2 call");
        person = personRepository.findById(id).orElseThrow();
        logger.info("3 call");
        person = personRepository.findById(id).orElseThrow();
        logger.info("4 call");
        person = personRepository.findPersonQuery(id);
        logger.info("5 call");
        person = personRepository.findPersonQuery(id);
        person.toString();
//        Hibernate.initialize(person.getChildren());
//        Hibernate.initialize(person.getList());
//        Hibernate.initialize(person.getMap());
        logger.info("return data" + session.getSessionFactory().getStatistics());
        return person;
    }
}
