package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Services.AccountService;
import com.example.bookingmakeup.Services.MakeupArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/makeup_artist")
public class MakeupArtistInforController {

    private final AccountService accountService;
    private final MakeupArtistService makeupArtistService;

    @Autowired
    public MakeupArtistInforController(AccountService accountService, MakeupArtistService makeupArtistService) {
        this.accountService = accountService;
        this.makeupArtistService = makeupArtistService;
    }

    // Hiển thị thông tin của artist đầu tiên
    @GetMapping("/infor.html")
    public String getArtistInfo(Model model) {
        List<MakeupArtist> artists = makeupArtistService.getAllMakeupArtists();
        MakeupArtist artist = artists.isEmpty() ? null : artists.get(0);
        model.addAttribute("artist", artist);
        return "makeup_artist/infor";
    }

    // Xử lý cập nhật thông tin
    @PostMapping("/update")
    public String updateArtistInfo(
            @RequestParam("id") Integer id,
            @RequestParam("nameAccount") String nameAccount,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("describes") String describes,
            @RequestParam("specialization") String specialization) {

        // Tìm MakeupArtist theo ID
        MakeupArtist artist = makeupArtistService.getAllMakeupArtists().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        // Cập nhật thông tin cho Account
        Account account = artist.getAccountMakeup();
        account.setPhone(phone);
        account.setAddress(address);
        // Lưu Account

        // Cập nhật thông tin cho MakeupArtist
        artist.setDescribes(describes);
        artist.setSpecialization(specialization);
        makeupArtistService.saveMakeupArtist(artist); // Lưu MakeupArtist

        // Chuyển hướng về trang thông tin
        return "redirect:/makeup_artist/infor.html";
    }
}