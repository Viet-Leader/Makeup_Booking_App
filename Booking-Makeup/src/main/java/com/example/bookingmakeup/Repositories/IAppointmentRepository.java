package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long appointmentId);
    List<Appointment> findAll();
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.status <> 'cancelled'")
    long countActiveAppointments();
    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.customer " +
            "JOIN FETCH a.service " +
            "JOIN FETCH a.branch")
    List<Appointment> findAllWithDetails();
    List<Appointment> findByBranch_BranchId(Long branchId);
}
