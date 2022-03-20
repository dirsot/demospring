package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "ADDRESS_STREET", nullable = true)
    private String street;
}
