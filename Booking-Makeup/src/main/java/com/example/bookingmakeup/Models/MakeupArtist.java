package com.example.bookingmakeup.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "makeup_artists")
public class MakeupArtist {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Account accountMakeup;

    @Lob
    @Column(name = "specialization")
    private String specialization;

    @Lob
    @Column(name = "describes")
    private String describes;

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

}