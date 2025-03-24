package com.example.bookingmakeup.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class BranchServiceId implements java.io.Serializable {
    private static final long serialVersionUID = -1191377172704851269L;
    @Column(name = "branch_id", nullable = false)
    private Integer branchId;

    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BranchServiceId entity = (BranchServiceId) o;
        return Objects.equals(this.branchId, entity.branchId) &&
                Objects.equals(this.serviceId, entity.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchId, serviceId);
    }

}