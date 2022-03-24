package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Child implements Serializable {
    @Id
    @GeneratedValue  //set in constructor
    @Column(nullable = false)
    private Long id;

    private String name;

    @Transient
    private String transientfield;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private List<Person> person = new ArrayList<>();

    public Child(String name) {
        this.name = name;
    }
}
