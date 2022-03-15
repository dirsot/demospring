package com.example.demo.controler;

import com.example.demo.Person;
import com.example.demo.repo.PersonRepository;
import com.example.demo.service.RabbitSendService;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/person")
public class MainController {

    @Autowired
    RabbitSendService rabbitSendService;
    @Autowired
    RestTemplate restTemplate;


    private final Logger logger = Logger.getLogger(MainController.class);
    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/")
    public String home() {
//        rabbitSendService.send(new Person("nam","doe"));
        logger.info("get method");
        return "get method";
    }

    @Retry(name = "retryWithFallback", fallbackMethod = "getDefaultPerson")
    @GetMapping(value = "/check")
    public Person check() {
        Person person = restTemplate.getForObject("http://localhost:8080/person/{id}",
                Person.class, 1);
        logger.info(person);
        return person;
    }

    public Person getDefaultPerson(RuntimeException ex) {
       return  new Person("0", "default", "default");
    }

    @GetMapping(value = "/all")
    public Iterable<Person> getAll() {
//        rabbitSendService.send(new Person("nam","doe"));
        logger.info("get all method");
        logger.info(personRepository.findAll());
        return personRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Person getPerson(@PathVariable Long id) {
        throw  new RuntimeException("failure");
//        logger.info("get person by id" + id);
//        return new Person(id.toString(), "name", "surname");
    }

    @GetMapping(value = "/add")
    public String addPerson(@RequestParam String name, @RequestParam String surname,
                            @AuthenticationPrincipal User user) {

        boolean hasUserRole = user.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (!hasUserRole) {
            return "no access from method";
        }
        Person person = new Person(name, surname);
        logger.info(personRepository.save(person));
        logger.info(user.getAuthorities());
        rabbitSendService.send(person);
        return "add method";
    }
}
