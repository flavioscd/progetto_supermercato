package com.example.demo.classes;

import jakarta.persistence.*;

@Entity
public class Prodotto {
    @Id
    @GeneratedValue(generator = "Prodotto_SEQ", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "Prodotto_SEQ", allocationSize = 1)
    private Long id;
    private String prodottoCode;
    private String description;
    private double price;
    private double points;

    public Prodotto() {

    }

    public Prodotto(String prodottoCode, String description, double price, double points) {
        this.prodottoCode = prodottoCode;
        this.description = description;
        this.price = price;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdottoCode() {
        return prodottoCode;
    }

    public void setProdottoCode(String prodottoCode) {
        this.prodottoCode = prodottoCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
