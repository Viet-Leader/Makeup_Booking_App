package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.*;
import com.example.bookingmakeup.Services.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {


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

    @GetMapping("")
    public String adminHome() {
        return "admin/index";
    }

    @GetMapping("/index.html")
    public String showAdminDashboard(Model model) {
        long totalStaff = branchStaffService.getTotalBranchesStaff();
        long totalStaff = branchStaffService.getTotalBranchesStaff();
        long totalBranches = branchService.getTotalBranches();
        long totalServices = serviceMakeUpService.getTotalSevice();
        long totalAppointments = appointmentService.getTotalActiveAppointments();

        var branches = branchService.getAllBranches();
        var services = serviceMakeUpService.getAllServices();

        model.addAttribute("totalStaff", totalStaff);
        model.addAttribute("totalBranches", totalBranches);
        model.addAttribute("totalServices", totalServices);
        model.addAttribute("totalAppointments", totalAppointments);
        model.addAttribute("branches", branches);
        model.addAttribute("services", services);

        return "admin/index";
    }

    @GetMapping("/appointment.html")
    public String appointmentManagement(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointments.forEach(appointment -> {
            Hibernate.initialize(appointment.getMakeupArtist());
            Hibernate.initialize(appointment.getService());
            Hibernate.initialize(appointment.getCustomer());
            Hibernate.initialize(appointment.getBranch());
        });
        model.addAttribute("appointments", appointments);
        return "admin/appointment";
    }

    @PostMapping("/update-status")
    @ResponseBody
    public String updateAppointmentStatus(@RequestParam Long appointmentId, @RequestParam String status) {
        List<String> validStatuses = List.of("pending", "confirmed", "completed", "cancelled");
        if (!validStatuses.contains(status.toLowerCase())) {
            return "Trạng thái không hợp lệ!";
        }

        boolean updated = appointmentService.updateAppointmentStatus(appointmentId, status.toLowerCase());
        return updated ? "Cập nhật trạng thái thành công!" : "Cuộc hẹn không tồn tại!";
    }

    @GetMapping("/appointment/{appointmentId}")
    @ResponseBody
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        if (appointment != null) {
            Hibernate.initialize(appointment.getCustomer());
            Hibernate.initialize(appointment.getService());
            Hibernate.initialize(appointment.getBranch());
            Hibernate.initialize(appointment.getMakeupArtist());
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/branch.html")
    public String branchManagement(Model model) {
        var branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        return "admin/branch";
    }

    @GetMapping("/branch/{branchId}")
    @ResponseBody
    public ResponseEntity<Branch> getBranch(@PathVariable Long branchId) {
        Branch branch = branchService.findBranchById(branchId);
        if (branch != null) {
            return ResponseEntity.ok(branch);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/branch")
    @ResponseBody
    public ResponseEntity<Void> addBranch(@RequestBody Branch branch) {
        branchService.saveBranch(branch);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/branch/{branchId}")
    @ResponseBody
    public ResponseEntity<Void> updateBranch(@PathVariable Long branchId, @RequestBody Branch branch) {
        branch.setBranchId(branchId);
        branchService.saveBranch(branch);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/branch/{branchId}")
    @ResponseBody
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/service.html")
    public String serviceManagement(Model model) {
        var services = serviceMakeUpService.getAllServices();
        model.addAttribute("services", services);
        return "admin/service";
    }

    @GetMapping("/service/{serviceId}")
    @ResponseBody
    public ResponseEntity<Optional<ServiceMakeUp>> getService(@PathVariable Long serviceId) {
        Optional<ServiceMakeUp> service = serviceMakeUpService.getServiceById(serviceId);
        if (service.isPresent()) {
            return ResponseEntity.ok(service);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/service")
    @ResponseBody
    public ResponseEntity<Void> addService(@RequestBody ServiceMakeUp service) {
        serviceMakeUpService.saveService(service);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/service/{serviceId}")
    @ResponseBody
    public ResponseEntity<Void> updateService(@PathVariable Long serviceId, @RequestBody ServiceMakeUp service) {
        service.setServiceId(serviceId);
        serviceMakeUpService.saveService(service);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/service/{serviceId}")
    @ResponseBody
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceMakeUpService.deleteService(serviceId);
        return ResponseEntity.ok().build();
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
