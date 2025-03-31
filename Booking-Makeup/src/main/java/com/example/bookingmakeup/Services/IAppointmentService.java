package com.example.bookingmakeup.Services;

import DTOs.AppointmentDTO;
import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Models.ServiceMakeUp;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAllAppointments();
    Appointment createAppointment(AppointmentDTO appointmentDTO);
    List<Branch> getAllBranches();
    List<MakeupArtist> getAllMakeupArtists();
    List<ServiceMakeUp> getAllServices();
}