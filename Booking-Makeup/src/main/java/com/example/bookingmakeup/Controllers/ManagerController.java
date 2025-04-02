package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Services.IAccountService;
import com.example.bookingmakeup.Services.IAppointmentService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
        // Lấy userId từ session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }

        // Lấy thông tin tài khoản từ userId
        Account account = accountService.findById(userId).orElse(null);

        if (account == null || account.getBranch() == null) {
            model.addAttribute("totalStaffs", 0);
        } else {
            Long branchId = account.getBranch().getBranchId();
            System.out.println("Branch ID: " + branchId); // Kiểm tra branchId
            model.addAttribute("totalStaffs", accountService.countStaffByBranch(branchId)); // Đếm nhân viên
        }

        // Lấy tổng số lịch hẹn
        model.addAttribute("totalAppointments", appointmentService.getTotalActiveAppointments());

        return "manager/index"; // Trả về trang Thymeleaf
    }
    @GetMapping("/appointment")
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
        return "manager/appointment";
    }
    @GetMapping("/staff")
    public String staffPage(Model model, HttpSession session) {
        // 🔍 Lấy userId từ session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }

        // ✅ Lấy thông tin tài khoản đăng nhập
        Account loggedInAccount = accountService.findById(userId).orElse(null);

        if (loggedInAccount == null || loggedInAccount.getBranch() == null) {
            return "redirect:/login"; // Nếu không tìm thấy tài khoản hoặc không có branch_id
        }

        // 📌 Lấy danh sách nhân viên cùng chi nhánh
        List<Account> staffList = accountService.getAccountsByBranch(userId);

        // ✅ Thêm danh sách nhân viên vào model để hiển thị trên trang staff.html
        model.addAttribute("staffList", staffList);

        return "manager/staff";
    }

    @GetMapping("/infor")
    public String getManagerInfor(Model model, HttpSession session) {
        // 🔍 Lấy userId từ session
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("🔍 DEBUG - userId trong session: " + userId);

        if (userId == null) {
            System.out.println("❌ Không tìm thấy userId, chuyển về login!");
            return "redirect:/login"; // Nếu chưa đăng nhập, yêu cầu đăng nhập lại
        }

        // ✅ Tìm kiếm tài khoản theo userId
        Account loggedInAccount = accountService.findById(userId).orElse(null);
        System.out.println("✅ DEBUG - Tài khoản tìm thấy: " + loggedInAccount);

        if (loggedInAccount == null) {
            System.out.println("❌ Không tìm thấy tài khoản với userId = " + userId);
            return "redirect:/login"; // Nếu tài khoản không tồn tại, quay về login
        }

        model.addAttribute("loggedInAccount", loggedInAccount);
        return "manager/infor";
    }

    // ✅ Xử lý cập nhật tài khoản
    @PostMapping("/updateAccount")
    public String updateAccount(@RequestParam Long userId,
                                @RequestParam String nameAccount,
                                @RequestParam String phone,
                                @RequestParam String address) {
        Account account = accountService.findById(userId).orElse(null);
        if (account == null) {
            return "redirect:/manager/infor"; // Không tìm thấy tài khoản
        }

        account.setNameAccount(nameAccount);
        account.setPhone(phone);
        account.setAddress(address);
        accountService.update(account); // Cập nhật tài khoản

        return "redirect:/manager/infor?success=true"; // Quay lại trang với thông báo thành công
    }
    @PostMapping("/update-status")
    @ResponseBody
    public String updateAppointmentStatus(@RequestParam Long appointmentId, @RequestParam String status) {
        boolean updated = appointmentService.updateAppointmentStatus(appointmentId, status);
        return updated ? "Cập nhật thành công" : "Cuộc hẹn không tồn tại";
    }
}
