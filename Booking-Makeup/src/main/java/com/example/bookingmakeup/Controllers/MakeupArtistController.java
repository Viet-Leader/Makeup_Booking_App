package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Services.IMakeupArtistService;
import com.example.bookingmakeup.Services.MakeupArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/makeup-artists")
public class MakeupArtistController {

    @Autowired
    private IMakeupArtistService makeupArtistService;

    @GetMapping
    public String getMakeupArtists(Model model) {
        List<MakeupArtist> artists = makeupArtistService.getAllMakeupArtists();
        System.out.println("Danh sách nghệ sĩ: " + artists); // Debug xem có dữ liệu không
        model.addAttribute("artists", artists);
        return "makeup-artist"; // Đảm bảo có file templates/makeup-artist.html
    }
    /*@GetMapping("/{id}")
    public String getMakeupArtistById(@PathVariable Integer id, Model model) {
        Optional<MakeupArtist> artist = makeupArtistService.getMakeupArtistById(id);
        artist.ifPresent(a -> model.addAttribute("artist", a));
        return "makeup-artist-detail"; // Trả về file makeup-artist-detail.html
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("makeupArtist", new MakeupArtist());
        return "create-makeup-artist"; // Form tạo mới
    }

    @PostMapping("/create")
    public String createMakeupArtist(@ModelAttribute MakeupArtist makeupArtist) {
        makeupArtistService.saveMakeupArtist(makeupArtist);
        return "redirect:/makeup-artists"; // Chuyển hướng sau khi tạo
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<MakeupArtist> artist = makeupArtistService.getMakeupArtistById(id);
        artist.ifPresent(a -> model.addAttribute("makeupArtist", a));
        return "edit-makeup-artist"; // Form chỉnh sửa
    }

    @PostMapping("/edit/{id}")
    public String updateMakeupArtist(@PathVariable Integer id, @ModelAttribute MakeupArtist makeupArtist) {
        makeupArtist.setId(id);
        makeupArtistService.saveMakeupArtist(makeupArtist);
        return "redirect:/makeup-artists";
    }

    @GetMapping("/delete/{id}")
    public String deleteMakeupArtist(@PathVariable Integer id) {
        makeupArtistService.deleteMakeupArtist(id);
        return "redirect:/makeup-artists";
    }*/
}
