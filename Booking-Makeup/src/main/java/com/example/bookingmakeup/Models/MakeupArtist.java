package com.example.bookingmakeup.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "makeup_artists")
public class MakeupArtist {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false) // Đổi LAZY thành EAGER
    @JoinColumn(name = "user_id", nullable = false)
    private Account accountMakeup;

    @Lob
    @Column(name = "specialization")
    private String specialization;

    @Lob
    @Column(name = "describes")
    private String describes;

    @Column(name = "age")
    private Integer age;

    @Column(name = "price")
    private Double price;

    @Column(name = "feature_work")
    private String featureWork;

    public MakeupArtist() {}

    public MakeupArtist(Integer id, String specialization, String describes, Integer age, Double price, String featureWork) {
        this.id = id;
        this.specialization = specialization;
        this.describes = describes;
        this.age = age;
        this.price = price;
        this.featureWork = featureWork;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccountMakeup() {
        return accountMakeup;
    }

    public void setAccountMakeup(Account accountMakeup) {
        this.accountMakeup = accountMakeup;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFeatureWork() {
        return featureWork;
    }

    public void setFeatureWork(String featureWork) {
        this.featureWork = featureWork;
    }

}