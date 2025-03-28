package com.example.bookingmakeup.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "branch_staff")
public class BranchStaff {

    @EmbeddedId
    private BranchStaffId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false) // Đổi sang EAGER để tải dữ liệu ngay
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    @ManyToOne(fetch = FetchType.EAGER) // Quan hệ với Branch nếu cần
    @JoinColumn(name = "branch_id", nullable = false, insertable = false, updatable = false)
    private Branch branch;

    // Getters và Setters
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    // Phương thức tiện ích để truy cập từ template
    public String getName() {
        return user != null ? user.getNameAccount() : "N/A";
    }

    public String getRole() {
        return user != null ? user.getRole() : "N/A";
    }

    public String getEmail() {
        return user != null ? user.getEmail() : "N/A";
    }
}