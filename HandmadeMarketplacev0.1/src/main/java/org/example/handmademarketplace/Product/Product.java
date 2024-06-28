package org.example.handmademarketplace.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.handmademarketplace.Account.Account;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @Column(length = 2000)
    private String description;
    private Double price;
    private Integer amount;

    @Lob
    @Column( length = Integer.MAX_VALUE, nullable = false)
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    @JsonBackReference
    private Account seller = null;


    public Product() {
    }

    public Product(String name, String description, Double price, Integer amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
//        this.photo = photo;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}


