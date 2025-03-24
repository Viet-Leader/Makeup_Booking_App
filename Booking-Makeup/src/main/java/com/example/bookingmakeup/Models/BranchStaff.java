package com.example.bookingmakeup.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "branch_staff")
public class BranchStaff {
    @EmbeddedId
    private BranchStaffId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    public BranchStaffId getId() {
        return id;
    }

    public void setId(BranchStaffId id) {
        this.id = id;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

}