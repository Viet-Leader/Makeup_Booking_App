package com.example.bookingmakeup.Models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "branch_services")
public class BranchService {
    @EmbeddedId
    private BranchServiceId id;

    public BranchServiceId getId() {
        return id;
    }

    public void setId(BranchServiceId id) {
        this.id = id;
    }

}