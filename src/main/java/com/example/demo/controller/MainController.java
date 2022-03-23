package com.example.demo.controller;

import com.example.demo.Person;
import com.example.demo.repo.PersonRepository;
import com.example.demo.service.RabbitSendService;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;

@RestController // rest controller
@Profile("dev")
@RequestMapping(path = "/person")
//@Scope("prototype")
@Scope("singleton")
public class MainController implements InitializingBean, DisposableBean, FactoryBean<Person> {

    private final Logger logger = Logger.getLogger(MainController.class);
    @Autowired
    RabbitSendService rabbitSendService;
    @Autowired
    RestTemplate restTemplate;
    @Inject
    @Qualifier("singletonDouble")
    Double doubleSingleton;
    @Autowired
    @Qualifier("prototypeDouble")
    Double doublePrototype;
    @Value("#{1 lt 1}")
    private String value;
    @Value("${info.contact.email}")
    private String email;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/")
    public String home() {
//        rabbitSendService.send(new Person("nam","doe"));
        logger.info("get method");
        return "get method";
    }

    @GetMapping(value = "/scope", produces = "application/json")
    public HashMap<String, Object> scope(@CookieValue(required = false, name = "cookieName") String cookie,
                                         @RequestHeader(required = false, name = "Keep-Alive") String header) {
        logger.info("get method");
        HashMap<String, Object> map = new HashMap<>();
        map.put("singleton", doubleSingleton);
        map.put("prototype", doublePrototype);

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("new String('hello world').toUpperCase()");
        String message = exp.getValue(String.class);

        map.put("message", message);
        map.put("value", value);
        map.put("email", email);
        map.put("cookie", cookie);
        map.put("header", header);
        return map;
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
        return new Person(0L, "default", "default");
    }

    @GetMapping(value = "/all")
    public Iterable<Person> getAll() {
        return personRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Person getPerson(@PathVariable Long id) {
        throw new RuntimeException("failure");
    }

    //    @Transactional
    @GetMapping(value = "/safe/{id}")
    public Person getPerson2(@PathVariable Long id) {
        logger.info("get person by id" + id);
        Person a = personRepository.findById(id).get();

//        Person person = new Person("aaa", "aaa");
//        logger.info(personRepository.save(person));

        logger.info("again get person by id" + id);
        Person b = personRepository.findById(id).get();
        logger.info("== check " + (a == b));
        return b;
    }

    //@ExceptionHandler(RuntimeException.class)
    public String handleException(NullPointerException e) {
        return "error";
    }

    @GetMapping(value = "/add")
//    @Transactional
    @ResponseBody // redundant by restcontroller
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

    @PostConstruct
    public void init() {
        logger.info("all set init");

    }

    @Override
    public void afterPropertiesSet() {
        logger.info("all set after");
    }

    @Override
    public void destroy() {
        logger.info("ending");
    }

    @Override
    public Person getObject() {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
