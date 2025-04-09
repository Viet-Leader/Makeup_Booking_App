package com.example.bookingmakeup.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "makeup_artists")
public class MakeupArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makeup_artist_id", nullable=false)
    private Long makeupArtistId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false) // Đổi LAZY thành EAGER
    @JoinColumn(name = "user_id", nullable = false)
    private Account accountMakeup;

    @Lob
    @Column(name = "specialization")
    private String specialization;

    @Lob
    @Column(name = "describes")
    private String describes;

    @Column(name = "age")
    private Long age;

    @Column(name = "price")
    private Double price;

    @Column(name = "feature_work")
    private String featureWork;

    public MakeupArtist() {}

    public MakeupArtist( String specialization, String describes, Long age, Double price, String featureWork, Long makeupArtistId) {
        this.specialization = specialization;
        this.describes = describes;
        this.age = age;
        this.price = price;
        this.featureWork = featureWork;
        this.makeupArtistId = makeupArtistId;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
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

    public Long getMakeupArtistId() {
        return makeupArtistId;
    }
    public void setMakeupArtistId(Long makeupArtistId) {
        this.makeupArtistId = makeupArtistId;
    }

}