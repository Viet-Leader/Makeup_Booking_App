package com.example.bookingmakeup.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Trang chủ admin
    @GetMapping("/index.html")
    public String adminIndex() {
        return "admin/index";
    }

    // Quản lý cuộc hẹn
    @GetMapping("/appointment.html")
    public String appointmentManagement() {
        return "admin/appointment";
    }

    // Quản lý chi nhánh
    @GetMapping("/branch.html")
    public String branchManagement() {
        return "admin/branch";
    }

    // Quản lý dịch vụ
    @GetMapping("/service.html")
    public String serviceManagement() {
        return "admin/service";
    }

    // Quản lý cài đặt
    @GetMapping("/setting.html")
    public String settingManagement() {
        return "admin/setting";
    }

    // Quản lý nhân viên
    @GetMapping("/staff.html")
    public String staffManagement() {
        return "admin/staff";
    }
}