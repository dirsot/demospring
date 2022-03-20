package com.example.demo;

//import javax.persistence.Entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Long id;

    private final Long age =2L;

    @Formula("age+id")
    private Long sum;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "middle", columnDefinition = "varchar(60) default '-'")
    @Generated(GenerationTime.INSERT)
    private String middle;

    @Basic(optional = false)
    @Column(nullable = false)
    private String surname;

    @Column(name = "LAST_UPDATED")
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Embedded
    private Address homeAddress;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person(Long id, String name, String surname) {
        this.id=id;
        this.name = name;
        this.surname = surname;
    }

}
