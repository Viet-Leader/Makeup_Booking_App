package com.example.bookingmakeup.Controllers;
import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Models.ServiceMakeUp;
import com.example.bookingmakeup.Repositories.*;
import com.example.bookingmakeup.Services.AccountService;
import com.example.bookingmakeup.Services.AppointmentService;
import com.example.bookingmakeup.Services.MakeupArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/makeup_artist")
public class WorkScheduleController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private MakeupArtistService makeupArtistService;
    @GetMapping("/work_schedule.html")
    public String ViewCalendar (Model model) {
        // Giả sử makeup artist hiện tại có ID là 1 (có thể lấy từ session hoặc authentication)
        Integer makeupArtistId = 1;

        // Lấy tất cả lịch hẹn của makeup artist
        List<Appointment> appointments = appointmentService.getAllAppointments()
                .stream()
                .filter(appointment -> appointment.getMakeupArtist().getId().equals(makeupArtistId))
                .collect(Collectors.toList());

        // Truyền dữ liệu vào model
        model.addAttribute("appointments", appointments);

        return "makeup_artist/work_schedule";
    }
}
