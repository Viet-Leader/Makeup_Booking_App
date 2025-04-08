package com.example.bookingmakeup.Models;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", nullable = false)
    private Long appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Account customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "makeup_artist_id")
    private MakeupArtist makeupArtist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;  // Thêm quan hệ với bảng branches

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceMakeUp service;  // Thêm quan hệ với bảng services

    @Column(name = "appointment_date")
    private Instant appointmentDate;

    @Lob
    @Column(name = "status")
    private String status;

    // Getters and setters

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public MakeupArtist getMakeupArtist() {
        return makeupArtist;
    }

    public void setMakeupArtist(MakeupArtist makeupArtist) {
        this.makeupArtist = makeupArtist;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public ServiceMakeUp getService() {
        return service;
    }

    public void setService(ServiceMakeUp service) {
        this.service = service;
    }

    public Instant getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Instant appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
