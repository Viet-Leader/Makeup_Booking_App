package com.example.bookingmakeup.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class BranchStaffId implements java.io.Serializable {
    private static final long serialVersionUID = 1658460111669539114L;
    @Column(name = "branch_id", nullable = false)
    private Integer branchId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BranchStaffId entity = (BranchStaffId) o;
        return Objects.equals(this.branchId, entity.branchId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchId, userId);
    }

}