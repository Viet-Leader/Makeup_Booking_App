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

import java.util.List;

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
    public String receptionistHome() {
        return "receptionist/home";
    }

    @GetMapping("/customer.html")
    public String receptionistCustomer(Model model) {
        List<Account> customers = accountService.getAllCustomers(); // 🔹 Lấy danh sách khách hàng
        model.addAttribute("customers", customers); // 🔹 Thêm vào model

        return "receptionist/customer"; // ✅ Phải khớp với tên file trong `templates/receptionist/`
    }

    @GetMapping("/checkin.html")
    public String checkinPage(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getService());
            System.out.println("MakeupArtist: " + appointment.getMakeupArtist());
            System.out.println("Service: " + appointment.getService());
        });
        System.out.println("Appointments: " + appointments);
        model.addAttribute("appointments", appointments);
        return "receptionist/checkin";
    }

    @GetMapping("/appointment.html")
    public String appointmentPage(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getService());
            System.out.println("MakeupArtist: " + appointment.getMakeupArtist());
            System.out.println("Service: " + appointment.getService());
        });
        System.out.println("Appointments: " + appointments);
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
