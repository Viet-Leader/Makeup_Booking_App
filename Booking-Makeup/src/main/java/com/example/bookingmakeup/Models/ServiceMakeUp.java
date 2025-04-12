package com.example.bookingmakeup.Models;

import jakarta.persistence.*;
@Entity
@Table (name = "services")

public class ServiceMakeUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column (name = "service_id")
    private Long serviceId;

    @Column (name = "name_service")
    private String name;

    @Column (name="description")
    private String description;

    @Column (name = "price")
    private Double price;

    @Column (name = "duration")
    private Integer duration;

    @Column (name = "image")
    private String image;

    public ServiceMakeUp() {}
    public ServiceMakeUp(Long serviceId, String name, String description, Double price, Integer duration, String image) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.image = image;
    }
    public Long getServiceId() {
        return serviceId;
    }
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
