package com.example.bookingmakeup.Controllers;

<<<<<<< Updated upstream
=======
import com.example.bookingmakeup.Services.*;
import com.example.bookingmakeup.Models.BranchStaff;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
<<<<<<< Updated upstream
=======
    @Autowired
    private IBranchStaffService branchStaffService;
    @Autowired
    private IMakeupArtistService makeupArtistService;
    @Autowired
    private IServiceMakeUpService serviceMakeUpService;
    @Autowired
    private IBranchesService branchService;
    @Autowired
    private IAppointmentService appointmentService;

>>>>>>> Stashed changes

    @GetMapping("")
    public String adminHome() {
        return "admin/index";
    }

    // Các mapping khác giữ nguyên
    @GetMapping("/index.html")
    public String showAdminDashboard(Model model) {
        // Fetch total counts
        long totalStaff= branchStaffService.getTotalBranchesStaff();
        long totalBranches = branchService.getTotalBranches();
        long totalServices = serviceMakeUpService.getTotalSevice();
        long totalAppointments = appointmentService.getTotalActiveAppointments();

        // Fetch lists for branches and services
        var branches = branchService.getAllBranches();
        var services = serviceMakeUpService.getAllServices();

        // Add data to the model
        model.addAttribute("totalStaff", totalStaff);
        model.addAttribute("totalBranches", totalBranches);
        model.addAttribute("totalServices", totalServices);
        model.addAttribute("totalAppointments", totalAppointments);
        model.addAttribute("branches", branches);
        model.addAttribute("services", services);

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