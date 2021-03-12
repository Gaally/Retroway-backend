package com.gally.eretroway.models;


import javax.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus name;

    public Status(OrderStatus name) { this.name = name; }

    public Status() {

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getName() {
        return name;
    }

    public void setName(OrderStatus name) {
        this.name = name;
    }
}
