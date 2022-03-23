package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Child implements Serializable {
    @Id
//    @GeneratedValue  //set in constructor
    @Column(nullable = false)
    private Long cid;

    private String name;

    @Transient
    private String transientfield;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private List<Person> person = new ArrayList<>();

    public Child(String name) {
        this.name = name;
    }
}
