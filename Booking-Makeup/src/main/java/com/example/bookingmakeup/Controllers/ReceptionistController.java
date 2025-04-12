package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Services.IAccountService;
import com.example.bookingmakeup.Services.IAppointmentService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAppointmentService appointmentService;

    @GetMapping("")
    public String receptionistIndex() {
        return "receptionist/home";
    }

    @GetMapping("/home.html")
    public String receptionistHome(Model model) {
        // Lấy danh sách khách hàng
        List<Account> customers = accountService.getAllCustomers();
        model.addAttribute("totalCustomers", customers.size());

        // Lấy tất cả cuộc hẹn
        List<Appointment> appointments = appointmentService.getAllAppointments();

        // Tổng số lịch đặt
        model.addAttribute("totalAppointments", appointments.size());

        // Đã Check-In
        long checkedInCount = appointments.stream()
                .filter(a -> a.getStatus().equalsIgnoreCase("Confirm") || a.getStatus().equalsIgnoreCase("Đã Check-In"))
                .count();
        model.addAttribute("checkedInCount", checkedInCount);

        // Chờ Check-In
        long pendingCount = appointments.stream()
                .filter(a -> a.getStatus().equalsIgnoreCase("Pendding") || a.getStatus().equalsIgnoreCase("Chờ Check-In"))
                .count();
        model.addAttribute("pendingCheckinCount", pendingCount);

        // ✅ Lọc lịch hẹn hôm nay (dựa trên Instant)
        LocalDate today = LocalDate.now();
        ZoneId zoneId = ZoneId.systemDefault();

        List<Appointment> todayAppointments = appointments.stream()
                .filter(a -> a.getAppointmentDate() != null &&
                        a.getAppointmentDate().atZone(zoneId).toLocalDate().isEqual(today))
                .collect(Collectors.toList());

        // Tránh lỗi lazy loading
        todayAppointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getCustomer());
            Hibernate.initialize(appointment.getService());
            Hibernate.initialize(appointment.getMakeupArtist());
        });

        model.addAttribute("todayAppointments", todayAppointments);

        return "receptionist/home";
    }

    @GetMapping("/customer.html")
    public String receptionistCustomer(Model model) {
        List<Account> customers = accountService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "receptionist/customer";
    }

    @GetMapping("/checkin.html")
    public String checkinPage(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getService());
        });
        model.addAttribute("appointments", appointments);
        return "receptionist/checkin";
    }

    @GetMapping("/appointment.html")
    public String appointmentPage(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getService());
        });
        model.addAttribute("appointments", appointments);
        return "receptionist/appointment";
    }

    @PostMapping("/update-status")
    @ResponseBody
    public String updateAppointmentStatus(@RequestParam Long appointmentId, @RequestParam String status) {
        boolean updated = appointmentService.updateAppointmentStatus(appointmentId, status);
        return updated ? "Cập nhật thành công" : "Cuộc hẹn không tồn tại";
    }
}
