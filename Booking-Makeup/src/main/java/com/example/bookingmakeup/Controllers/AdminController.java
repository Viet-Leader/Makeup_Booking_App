package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Services.BranchStaffService;
import com.example.bookingmakeup.Models.BranchStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BranchStaffService branchStaffService;

    @Autowired
    public AdminController(BranchStaffService branchStaffService) {
        this.branchStaffService = branchStaffService;
    }

    @GetMapping("")
    public String adminHome() {
        return "admin/index";
    }

    @GetMapping("/index.html")
    public String adminIndex() {
        return "admin/index";
    }

    @GetMapping("/appointment.html")
    public String appointmentManagement() {
        return "admin/appointment";
    }

    @GetMapping("/branch.html")
    public String branchManagement() {
        return "admin/branch";
    }

    @GetMapping("/service.html")
    public String serviceManagement() {
        return "admin/service";
    }

    @GetMapping("/setting.html")
    public String settingManagement() {
        return "admin/setting";
    }

    @GetMapping("/staff.html")
    public String staffManagement(Model model) {
        List<BranchStaff> staffList = branchStaffService.getAllBranchStaff();
        model.addAttribute("staffList", staffList != null ? staffList : Collections.emptyList());
        return "admin/staff";
    }
}