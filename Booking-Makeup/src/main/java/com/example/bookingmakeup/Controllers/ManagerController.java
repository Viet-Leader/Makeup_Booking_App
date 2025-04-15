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
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAppointmentService appointmentService;

    @GetMapping("")
    public String mainPage() {
        return "manager/index";
    }

    @GetMapping("/index")
    public String indexPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Account account = accountService.findById(userId).orElse(null);

        if (account == null || account.getBranch() == null) {
            model.addAttribute("totalStaffs", 0);
        } else {
            Long branchId = account.getBranch().getBranchId();
            model.addAttribute("totalStaffs", accountService.countStaffByBranch(branchId));
        }

        model.addAttribute("totalAppointments", appointmentService.getTotalActiveAppointments());
        return "manager/index";
    }

    @GetMapping("/appointment")
    public String appointmentPage(Model model, HttpSession session) {


        // Lấy userId từ session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        // Lấy thông tin tài khoản
        Account loggedInAccount = accountService.findById(userId).orElse(null);

        if (loggedInAccount == null || loggedInAccount.getBranch() == null) {
            return "redirect:/login";
        }

        Long branchId = loggedInAccount.getBranch().getBranchId();

        // Lấy danh sách lịch hẹn theo chi nhánh
        List<Appointment> appointments = appointmentService.getAppointmentsByBranch(branchId);

        appointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getService());
        });
        System.out.println(appointments.size());
        model.addAttribute("appointments", appointments);
        return "manager/appointment";
    }

    @GetMapping("/staff")
    public String staffPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Account loggedInAccount = accountService.findById(userId).orElse(null);
        if (loggedInAccount == null || loggedInAccount.getBranch() == null) {
            return "redirect:/login";
        }

        List<Account> staffList = accountService.getAccountsByBranch(userId);
        model.addAttribute("staffList", staffList);
        return "manager/staff";
    }

    @GetMapping("/infor")
    public String getManagerInfor(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Account loggedInAccount = accountService.findById(userId).orElse(null);
        if (loggedInAccount == null) {
            return "redirect:/login";
        }

        model.addAttribute("loggedInAccount", loggedInAccount);
        return "manager/infor";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@RequestParam Long userId,
                                @RequestParam String nameAccount,
                                @RequestParam String phone,
                                @RequestParam String address) {
        Account account = accountService.findById(userId).orElse(null);
        if (account == null) {
            return "redirect:/manager/infor";
        }

        account.setNameAccount(nameAccount);
        account.setPhone(phone);
        account.setAddress(address);
        accountService.update(account);

        return "redirect:/manager/infor?success=true";
    }

    @PostMapping("/update-status")
    @ResponseBody
    public String updateAppointmentStatus(@RequestParam Long appointmentId, @RequestParam String status) {
        boolean updated = appointmentService.updateAppointmentStatus(appointmentId, status);
        return updated ? "Cập nhật thành công" : "Cuộc hẹn không tồn tại";
    }
}
