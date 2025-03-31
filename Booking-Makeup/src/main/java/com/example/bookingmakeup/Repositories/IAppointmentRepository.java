package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long appointmentId);
    List<Appointment> findAll();
}
