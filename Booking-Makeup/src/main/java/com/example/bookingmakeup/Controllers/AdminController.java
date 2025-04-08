package com.example.bookingmakeup.Controllers;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import com.example.bookingmakeup.Services.*;
import com.example.bookingmakeup.Models.BranchStaff;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
=======
import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Services.*;
import com.example.bookingmakeup.Models.BranchStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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

    @GetMapping("/appointment.html")
    public String appointmentManagement() {
        return "admin/appointment";
    }

    @GetMapping("/branch.html")
    public String branchManagement(Model model) {
        var branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        return "admin/branch";
    }

    // Lấy thông tin chi nhánh theo ID (cho chức năng chỉnh sửa)
    @GetMapping("/branch/{branchId}")
    @ResponseBody
    public ResponseEntity<Branch> getBranch(@PathVariable Long branchId) {
        Branch branch = branchService.findBranchById(branchId);
        if (branch != null) {
            return ResponseEntity.ok(branch);
        }
        return ResponseEntity.notFound().build();
    }

    // Thêm chi nhánh mới
    @PostMapping("/branch")
    @ResponseBody
    public ResponseEntity<Void> addBranch(@RequestBody Branch branch) {
        branchService.saveBranch(branch);
        return ResponseEntity.ok().build();
    }

    // Cập nhật chi nhánh
    @PutMapping("/branch/{branchId}")
    @ResponseBody
    public ResponseEntity<Void> updateBranch(@PathVariable Long branchId, @RequestBody Branch branch) {
        branch.setBranchId(branchId); // Đảm bảo ID khớp
        branchService.saveBranch(branch);
        return ResponseEntity.ok().build();
    }

    // Xóa chi nhánh
    @DeleteMapping("/branch/{branchId}")
    @ResponseBody
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.ok().build();
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