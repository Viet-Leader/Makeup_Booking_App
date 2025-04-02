package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/makeup_artist")
public class MakeupArtistIndexController {

    private final AppointmentService appointmentService;

    @Autowired
    public MakeupArtistIndexController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/index.html")
    public String appointment(Model model) {
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        if (allAppointments == null) {
            allAppointments = Collections.emptyList();
        }

        // Lọc các lịch hẹn hôm nay, đảm bảo kết quả không null
        LocalDate today = LocalDate.now();
        List<Appointment> todayAppointments = allAppointments.stream()
                .filter(a -> a.getAppointmentDate() != null &&
                        LocalDate.ofInstant(a.getAppointmentDate(), ZoneId.systemDefault()).equals(today))
                .collect(Collectors.toList());

        // Lọc các lịch hẹn sắp tới, đảm bảo kết quả không null
        List<Appointment> upcomingAppointments = allAppointments.stream()
                .filter(a -> a.getAppointmentDate() != null &&
                        LocalDate.ofInstant(a.getAppointmentDate(), ZoneId.systemDefault()).isAfter(today))
                .collect(Collectors.toList());

        // Thêm vào mô hình, đảm bảo không bao giờ là null
        model.addAttribute("todayAppointments", todayAppointments != null ? todayAppointments : Collections.emptyList());
        model.addAttribute("upcomingAppointments", upcomingAppointments != null ? upcomingAppointments : Collections.emptyList());

        return "makeup_artist/index";
    }
}