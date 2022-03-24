package com.example.demo.service;

import com.example.demo.Person;
import org.springframework.stereotype.Component;

@Component
public interface PersonService {
    public Person getPerson(long id);
}
