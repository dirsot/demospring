package com.example.demo;

//import javax.persistence.Entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

//mapping of inheritanca classe, with abstract class
//primary key generators
//secondary table

// security block access to page
//actuator


@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})},
        indexes = @Index(columnList = "name", name = "IndexName"))
@SecondaryTable(name = "person2")
@Check(constraints = "regexp_like(surname,'^a')")
public class Person implements Serializable {

    private final Long age = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Long id;

    @Formula("age+id")
    private Long sum;

    @NotNull
    private String name;

    @NotNull
    @Column(table = "person2", name = "middle", columnDefinition = "varchar(60) default '-'")
    @Generated(GenerationTime.INSERT)
    private String middle;

    @Basic(optional = false)
    @Column(nullable = false)
    private String surname;

    @Column(name = "LAST_UPDATED")
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, updatable = true)
    private Date startDate;

    @ElementCollection
//    @SortNatural
    @Cascade(value = CascadeType.ALL)
    private List<String> list = new ArrayList<>();

    @ElementCollection
    private Map<String, Long> map = new HashMap<>();

    @Embedded
    private Address homeAddress;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
        list.add("bb");
        list.add("aa");
        map.put("bb", 1L);
        map.put("aa", 2L);
    }

    public Person(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

}
