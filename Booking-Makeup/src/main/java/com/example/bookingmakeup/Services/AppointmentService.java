package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Repositories.IAppointmentRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {
    @Autowired
    private IAppointmentRepository appointmentRepository;

    @Override
    public boolean updateAppointmentStatus(Long appointmentId, String newStatus) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(newStatus);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    @Override
    public long getTotalActiveAppointments() {
        return appointmentRepository.countActiveAppointments();
    }
    @Override
    public Appointment findAppointmentById(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            Hibernate.initialize(appointment.getCustomer());
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getBranch());
            Hibernate.initialize(appointment.getService());
            return appointment;
        }
        return null;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        appointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getCustomer());
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getBranch());
            Hibernate.initialize(appointment.getService());
        });
        return appointments;
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
