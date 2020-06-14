package com.curtisvanzandt.vaadinshoeinventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Shoe {

    @Id
    @GeneratedValue
    private Long id;

    private String brand;

    private Integer size;

    protected Shoe() {}

    public Shoe(final String brand, final Integer size) {
        this.brand = brand;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", size=" + size +
                '}';
    }
}
