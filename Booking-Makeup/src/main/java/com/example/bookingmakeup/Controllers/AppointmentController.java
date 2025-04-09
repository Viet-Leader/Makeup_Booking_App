package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Appointment;
import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Models.ServiceMakeUp;
import com.example.bookingmakeup.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/schedule.html")
public class AppointmentController {

    @Autowired
    private IAppointmentRepository appointmentRepository;

    @Autowired
    private IBranchRepository branchRepository;

    @Autowired
    private IMakeupArtistRepository makeupArtistRepository;

    @Autowired
    private IServiceMakeUpRepositories serviceRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @GetMapping("/new")
    public String showAppointmentForm(Model model) {
        try {
            List<Branch> branches = branchRepository.findAll();
            List<MakeupArtist> artists = makeupArtistRepository.findAll();
            List<ServiceMakeUp> services = serviceRepository.findAll();

            System.out.println("Branches: " + branches);
            System.out.println("Artists: " + artists);
            System.out.println("Services: " + services);

            model.addAttribute("branches", branches);
            model.addAttribute("artists", artists);
            model.addAttribute("services", services);
            model.addAttribute("appointment", new Appointment());

            return "schedule";
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Tạo file error.html để hiển thị lỗi
        }
    }
    @PostMapping("/create")
    public String createAppointment(@ModelAttribute Appointment appointment,
                                    @RequestParam Long customerId,
                                    @RequestParam Integer branchId,
                                    @RequestParam Integer artistId,
                                    @RequestParam Integer serviceId,
                                    @RequestParam String appointmentDate,
                                    @RequestParam String appointmentTime) {

        // Convert date and time to Instant
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDate + "T" + appointmentTime);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();

        appointment.setAppointmentDate(instant);
        appointment.setStatus("PENDING");


        appointmentRepository.save(appointment);
        return "schedule";
    }
}