package com.example.bookingmakeup.Controllers;
import com.example.bookingmakeup.Models.*;
import com.example.bookingmakeup.Repositories.IAppointmentRepository;
import com.example.bookingmakeup.Services.IAppointmentService;
import com.example.bookingmakeup.Services.IBranchesService;
import com.example.bookingmakeup.Services.IMakeupArtistService;
import com.example.bookingmakeup.Services.IServiceMakeUpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    private IServiceMakeUpService serviceMakeUpService;
    @Autowired
    private IBranchesService branchesService;
    @Autowired
    private IMakeupArtistService makeupArtistService;

    @GetMapping("/schedule")
    public ModelAndView schedulePage(HttpSession session) {
        // Kiểm tra xem session có chứa thông tin đăng nhập không
        if (session.getAttribute("user") == null) {
            return new ModelAndView("redirect:/login"); // Chuyển hướng nếu chưa đăng nhập
        }

        return new ModelAndView("schedule"); // Hiển thị trang nếu đã đăng nhập
    }
    @GetMapping
    public String getAppointment(Model model,HttpSession session) {
        Account user = (Account) session.getAttribute("user"); // Lấy khách hàng từ session
        if (user == null) {
            return "redirect:/login";
        }
        List<Branch> branches = branchesService.getAllBranches();
        model.addAttribute("branches", branches);
        List<MakeupArtist> makeupArtists = makeupArtistService.getAllMakeupArtists();
        model.addAttribute("makeupArtists", makeupArtists);
        List<ServiceMakeUp> serviceMakeUps = serviceMakeUpService.getAllServices();
        model.addAttribute("serviceMakeUps", serviceMakeUps);
        List<Appointment> appointments = appointmentService.getAllAppointments();
        System.out.println("Danh sách nghệ sĩ: " + appointments); // Debug xem có dữ liệu không
        model.addAttribute("appointments", appointments);
        return "/schedule"; // Đảm bảo có file templates/makeup-artist.html
    }
    @PostMapping("/save")
    public String saveAppointment(@RequestParam("branchId") Long branchId,
                                  @RequestParam("makeupArtistId") Long makeupArtistId,
                                  @RequestParam("serviceId") Long serviceId,
                                  @RequestParam("appointmentDate") String appointmentDateStr,
                                  HttpSession session) {

        Account user = (Account) session.getAttribute("user"); // Lấy khách hàng từ session
        if (user == null) {
            return "redirect:/login";
        }

        Appointment appointment = new Appointment();

        // Lấy các thực thể theo ID
        Branch branch = branchesService.findBranchById(branchId);
        MakeupArtist artist = makeupArtistService.getMakeupArtistByMakeupArtistId(makeupArtistId).orElse(null);
        ServiceMakeUp service = serviceMakeUpService.getServiceById(serviceId).orElse(null);

        // Chuyển đổi ngày
        Instant date = Instant.parse(appointmentDateStr + ":00Z"); // Ex: "2025-04-11T10:00"

        // Gán vào đối tượng
        appointment.setCustomer(user);
        appointment.setBranch(branch);
        appointment.setMakeupArtist(artist);
        appointment.setService(service);
        appointment.setAppointmentDate(date);
        appointment.setStatus("pending"); // Mặc định chờ xác nhận

        // Lưu vào DB
        appointmentService.save(appointment);

        return "redirect:/schedule"; // Sau khi lưu, quay về trang đặt lịch
    }

}
