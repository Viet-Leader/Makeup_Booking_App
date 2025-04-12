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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
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
    public String ViewCalendar (Model model)   {
        try{
        List<Appointment> allAppointments = appointmentService.getAllAppointmentsWithDetails();
            model.addAttribute("appointments", allAppointments);}
        catch(Exception e){
            model.addAttribute("errorMessage", "Không thể tải danh sách cuộc hẹn. Chi tiết lỗi: " + e.getMessage());
        }
        return "makeup_artist/work_schedule";
    }
}
