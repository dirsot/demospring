package com.example.demo.service;

import com.example.demo.Person;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderReceiver {
    private Logger logger = Logger.getLogger(JmsOrderReceiver.class);
    private JmsTemplate jms;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms) {
        this.jms = jms;
    }

    @JmsListener(destination = "test.queue")
    public void receiveOrder(Person person) {
        logger.info("JMS listener " + person);
    }
}
