package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Services.IAccountService;
import com.example.bookingmakeup.Services.IAppointmentService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    IAccountService accountService;
    @Autowired
    IAppointmentService appointmentService;

    @GetMapping("/infor")
    public String inforPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("DEBUG - userId trong session: " + userId);

        if (userId == null) {
            System.out.println("Không tìm thấy userId, chuyển về login!");
            return "redirect:/login";
        }

        Account loggedInAccount = accountService.findById(userId).orElse(null);
        if (loggedInAccount == null) {
            System.out.println("Không tìm thấy tài khoản với userId = " + userId);
            return "redirect:/login";
        }

        //  Thêm đoạn này để lấy danh sách lịch hẹn
        List<Appointment> appointments = appointmentService.getAppointmentByCustomerId(userId);
        if (appointments == null || appointments.isEmpty()) {
            model.addAttribute("noAppointments", true);
        } else {
            model.addAttribute("appointments", appointments);
        }

        model.addAttribute("loggedInAccount", loggedInAccount);
        model.addAttribute("appointments", appointments); // Truyền danh sách xuống view
        return "customer/infor";
    }
    @PostMapping("/updateAccount")
    public String updateAccount(@RequestParam Long userId,
                                @RequestParam String phone,
                                @RequestParam String address) {
        Account account = accountService.findById(userId).orElse(null);
        if (account == null) {
            return "redirect:/customer/infor"; // Không tìm thấy tài khoản
        }

        account.setPhone(phone);
        account.setAddress(address);
        accountService.update(account); // Cập nhật tài khoản

        return "redirect:/customer/infor?success=true"; // Quay lại trang với thông báo thành công
    }
    @PostMapping("/update-status")
    @ResponseBody
    public String updateAppointmentStatus(@RequestParam Long appointmentId, @RequestParam String status) {
        boolean updated = appointmentService.updateAppointmentStatus(appointmentId, status);
        return updated ? "Cập nhật thành công" : "Cuộc hẹn không tồn tại";
    }
}
