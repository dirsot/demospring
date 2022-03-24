package com.example.demo.service;

import com.example.demo.Person;
import com.example.demo.repo.PersonRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;

@Service("personService")
public class PersonServiceImpl implements PersonService {
    private final Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager entityManager;

    // JPA doesn't enforce transactions on read operations. select statement works event without @Transactional.

    @Transactional
    @Override
    public Person getPerson(long id) {
        logger.info("Transaction name: " + TransactionSynchronizationManager.getCurrentTransactionName());

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
        //logger.info("return data " + session.getSessionFactory().getStatistics());
        return person;
    }

    public void query(EntityManager entityManager) {
        jdbcTemplate.update("update person set name='aaname' where id = 1");

        String queryStr = "select id,name from person limit 1;";
        Object[] object = (Object[]) entityManager.createNativeQuery(queryStr).getSingleResult();
        logger.info("SQL query " + object[0] + " - " + object[1]);
    }
}
