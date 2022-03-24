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
//dirty read - read uncommited
//phantom read - second read returns diff data

//read uncommited/read comm/repread/ serializbled

// security block access to page
//actuator

//get - eager, returns null/ load - lazy returns proxy ,throws exc now or later

//s.save

//attach object
//s.update
//s/lock

//s.merge - merged transient/detached object or creates new persistent

//s.refresh
//s.evict(val) -detach object
//s.clear detach all

//s.flush - execute db operation

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})},
        indexes = @Index(columnList = "name", name = "IndexName"))
@SecondaryTable(name = "person2")
@Check(constraints = "regexp_like(surname,'^a')")
@FilterDef(name = "idFilter", parameters = @ParamDef(name = "fromId", type = "long"))
@Filter(name = "idFilter", condition = "id > :fromId")
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

    @Version
    private int version;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @Filter(name = "idFilter", condition = "id > :fromId")
    private List<Child> children = new ArrayList<>();

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
        list.add("bb");
        list.add("aa");
        map.put("bb", 1L);
        map.put("aa", 2L);
//        children.add(new Child(1L, "name", "tran"));
//        children.add(new Child(2L, "name2", "tran"));
        children.add(new Child("name"));
        children.add(new Child("name2"));
    }

    public Person(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

}
