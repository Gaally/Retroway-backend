package com.gally.eretroway.models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ordered")
    private LocalDateTime ordered;

    @Column(name = "shipped")
    private LocalDateTime shipped;

    @Column(name = "ship_to")
    private String shipTo;

    @Column(name = "total_cost")
    private int totalCost;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "status_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    private Set<Status> statuses = new HashSet<>();


    public Order(User user, LocalDateTime ordered, LocalDateTime shipped, String shipTo, int totalCost) {
        this.user = user;
        this.ordered = ordered;
        this.shipped = shipped;
        this.shipTo = shipTo;
        this.totalCost = totalCost;
    }

    public Order() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrdered() {
        return ordered;
    }

    public void setOrdered(LocalDateTime ordered) {
        this.ordered = ordered;
    }

    public LocalDateTime getShipped() {
        return shipped;
    }

    public void setShipped(LocalDateTime shipped) {
        this.shipped = shipped;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }




}
