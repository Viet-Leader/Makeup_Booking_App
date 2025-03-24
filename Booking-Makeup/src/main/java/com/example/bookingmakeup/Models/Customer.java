package com.example.bookingmakeup.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Account accountMakeup;

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

}