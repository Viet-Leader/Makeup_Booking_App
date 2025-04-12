package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Appointment;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAllAppointments();
    boolean updateAppointmentStatus(Long appointmentId, String newStatus);
    long getTotalActiveAppointments();
    Appointment findAppointmentById(Long id);

    void saveAppointment(Appointment appointment);

}
