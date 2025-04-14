package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.*;
import com.example.bookingmakeup.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/makeup_artist")
public class MakeupArtistIndexController {

    private final AppointmentService appointmentService;
    private final AccountService accountService;
    private final MakeupArtistService makeupArtistService;

    @Autowired
    public MakeupArtistIndexController(AppointmentService appointmentService,
                                       AccountService accountService,
                                       MakeupArtistService makeupArtistService) {
        this.appointmentService = appointmentService;
        this.accountService = accountService;
        this.makeupArtistService = makeupArtistService;
    }

    // Trang chủ Makeup Artist: Lịch hẹn hôm nay và sắp tới
    @GetMapping("/index.html")
    public String appointment(Model model) {
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        if (allAppointments == null) {
            allAppointments = Collections.emptyList();
        }

        LocalDate today = LocalDate.now();
        List<Appointment> todayAppointments = allAppointments.stream()
                .filter(a -> a.getAppointmentDate() != null &&
                        LocalDate.ofInstant(a.getAppointmentDate(), ZoneId.systemDefault()).equals(today))
                .collect(Collectors.toList());

        List<Appointment> upcomingAppointments = allAppointments.stream()
                .filter(a -> a.getAppointmentDate() != null &&
                        LocalDate.ofInstant(a.getAppointmentDate(), ZoneId.systemDefault()).isAfter(today))
                .collect(Collectors.toList());

        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("upcomingAppointments", upcomingAppointments);

        return "makeup_artist/index";
    }

    // Trang xem lịch làm việc
    @GetMapping("/work_schedule.html")
    public String viewCalendar(Model model) {
        try {
            List<Appointment> allAppointments = appointmentService.getAllAppointmentsWithDetails();
            // Chuẩn hóa múi giờ nếu cần
            allAppointments.forEach(appointment -> {
                if (appointment.getAppointmentDate() != null) {
                    // Chuyển đổi Instant sang LocalDateTime với múi giờ cụ thể (ví dụ: UTC+7)
                    LocalDateTime dateTime = LocalDateTime.ofInstant(
                            appointment.getAppointmentDate(), ZoneId.of("Asia/Ho_Chi_Minh"));
                    // Đặt lại giờ về 00:00:00 nếu cần
                    dateTime = dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
                    appointment.setAppointmentDate(dateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant());
                }
            });
            model.addAttribute("appointments", allAppointments);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể tải danh sách cuộc hẹn. Chi tiết lỗi: " + e.getMessage());
        }
        return "makeup_artist/work_schedule";
    }

    // Trang thông tin makeup artist
    @GetMapping("/infor.html")
    public String getArtistInfo(Model model) {List<MakeupArtist> artists = makeupArtistService.getAllMakeupArtists();
        MakeupArtist artist = artists.isEmpty() ? null : artists.get(0);
        model.addAttribute("artist", artist);
        return "makeup_artist/infor";
    }

    // Cập nhật thông tin makeup artist
    @PostMapping("/update")
    public String updateArtistInfo(
            @RequestParam("id") Long makeupArtistId,
            @RequestParam("nameAccount") String nameAccount,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("describes") String describes,
            @RequestParam("specialization") String specialization) {

        MakeupArtist artist = makeupArtistService.getAllMakeupArtists().stream()
                .filter(a -> a.getMakeupArtistId().equals(makeupArtistId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        Account account = artist.getAccountMakeup();
        account.setPhone(phone);
        account.setAddress(address);

        artist.setDescribes(describes);
        artist.setSpecialization(specialization);

        makeupArtistService.saveMakeupArtist(artist);

        return "redirect:/makeup_artist/infor.html";
    }
}