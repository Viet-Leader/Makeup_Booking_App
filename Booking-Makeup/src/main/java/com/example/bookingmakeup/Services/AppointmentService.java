package com.example.bookingmakeup.Services;

import DTOs.AppointmentDTO;
import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Models.ServiceMakeUp;
import com.example.bookingmakeup.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private IAppointmentRepository appointmentRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IBranchRepository branchRepository;

    @Autowired
    private IMakeupArtistRepository makeupArtistRepository;

    @Autowired
    private IServiceMakeUpRepositories serviceRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();

        appointment.setCustomer(accountRepository.findById(appointmentDTO.getCustomerId()).orElse(null));
        appointment.setMakeupArtist(accountRepository.findById(appointmentDTO.getMakeupArtistId().longValue()).orElse(null));
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setStatus(appointmentDTO.getStatus());

        return appointmentRepository.save(appointment);
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public List<MakeupArtist> getAllMakeupArtists() {
        return makeupArtistRepository.findAll();
    }

    public List<ServiceMakeUp> getAllServices() {
        return serviceRepository.findAll();
    }
}