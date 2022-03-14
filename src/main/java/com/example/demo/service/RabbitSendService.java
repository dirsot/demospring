package com.example.demo.service;

import com.example.demo.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitSendService {
    private RabbitTemplate rabbitTemplate;
    private JmsTemplate jms;

    @Autowired
    public RabbitSendService(RabbitTemplate rabbitTemplate, JmsTemplate jms) {

        this.rabbitTemplate = rabbitTemplate;
        this.jms = jms;
    }

    public void send(Person person) {
        jms.convertAndSend(person);
//        rabbitTemplate.convertAndSend( person);
    }
}
