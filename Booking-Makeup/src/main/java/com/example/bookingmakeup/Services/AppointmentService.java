package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Repositories.IAppointmentRepository;
import jakarta.transaction.Transactional;
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
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

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

}

package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Repositories.IAppointmentRepository;
import jakarta.transaction.Transactional;
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
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

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
}
