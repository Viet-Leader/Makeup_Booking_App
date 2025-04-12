package com.example.bookingmakeup.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "account_makeup")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'customer'")
    private String role;

    @Column(name = "name_account", unique = true)
    private String nameAccount;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column (name = "address", unique = true)
    private String address;

    @Column(name = "rating", columnDefinition = "DOUBLE DEFAULT 0.0")
    private double rating;

    @Column (name = "avatar", unique = true)
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id", foreignKey = @ForeignKey(name = "fk_branch"))
    private Branch branch;

    public Account() {
    }

    // Constructor đầy đủ tham số
    public Account(Long userId, String email, String password, String role, String nameAccount,
                   String phone, String address, double rating, String avatar) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nameAccount = nameAccount;
        this.phone = phone;
        this.address = address;
        this.rating = rating;
        this.avatar = avatar;
        this.branch = branch;
    }

    // 🔹 Constructor đơn giản - Để đăng ký tài khoản với giá trị mặc định
    public Account(String email, String password, String nameAccount, Branch branch) {
        this.email = email;
        this.password = password;
        this.nameAccount = nameAccount;
        this.role = "CUSTOMER"; // Mặc định
        this.phone = ""; // Trống để user tự điền sau
        this.address = ""; // Trống để user tự điền sau
        this.rating = 0.0; // Mặc định
        this.avatar = "default.png"; // Mặc định
        this.branch = branch;
    }


    // Getter và Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
