package com.gally.eretroway.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "ordered")
    private Date ordered;

    @Column(name = "shipped")
    private Date shipped;

    @Column(name = "ship_to")
    private String shipTo;

    @Column(name = "total_cost")
    private Double totalCost;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id,", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;
    @JoinColumn(name = "ship_to", referencedColumnName = "address", insertable = false, updatable = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
    private Set<Status> status = new HashSet<>();

    public Order() {

    }

    public Order(Long id, @NotBlank Integer userId, @NotBlank Long productId, Date ordered, Date shipped, String shipTo, Double totalCost, Product product, User user) {
        Id = id;
        this.userId = userId;
        this.productId = productId;
        this.ordered = ordered;
        this.shipped = shipped;
        this.shipTo = shipTo;
        this.totalCost = totalCost;
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getOrdered() {
        return ordered;
    }

    public void setOrdered(Date ordered) {
        this.ordered = ordered;
    }

    public Date getShipped() {
        return shipped;
    }

    public void setShipped(Date shipped) {
        this.shipped = shipped;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}