package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Comment;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Services.ICommentService;
import com.example.bookingmakeup.Services.IMakeupArtistService;
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
    @Autowired
    private ICommentService commentService;

    @GetMapping
    public String getMakeupArtists(Model model) {
        List<MakeupArtist> artists = makeupArtistService.getAllMakeupArtists();
        System.out.println("Danh sách nghệ sĩ: " + artists); // Debug xem có dữ liệu không
        model.addAttribute("artists", artists);
        return "/makeup-artist"; // Đảm bảo có file templates/makeup-artist.html
    }
    @GetMapping("/{makeupArtistId}")
    public String getMakeupArtistDetail(@PathVariable("makeupArtistId") Long makeupArtistId, Model model) {
        Optional<MakeupArtist> artistOpt = makeupArtistService.getMakeupArtistByMakeupArtistId(makeupArtistId);
        if (artistOpt.isPresent()) {
            MakeupArtist artist = artistOpt.get();
            model.addAttribute("artistDetail", artist);

            // Kiểm tra danh sách bình luận
            List<Comment> comments = commentService.getCommentsByMakeupArtistId(makeupArtistId);
            System.out.println("Số bình luận của nghệ sĩ " + makeupArtistId + ": " + comments.size());
            model.addAttribute("comments", comments);

            return "artistdetail"; // Trang hiển thị chi tiết
        }
        return "redirect:/makeup-artists";
    }
/*
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
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<MakeupArtist> artist = makeupArtistService.getMakeupArtistById(id);
        artist.ifPresent(a -> model.addAttribute("makeupArtist", a));
        return "edit-makeup-artist"; // Form chỉnh sửa
    }

    @PostMapping("/edit/{id}")
    public String updateMakeupArtist(@PathVariable Long id, @ModelAttribute MakeupArtist makeupArtist) {
        makeupArtist.setId(id);
        makeupArtistService.saveMakeupArtist(makeupArtist);
        return "redirect:/makeup-artists";
    }

    @GetMapping("/delete/{id}")
    public String deleteMakeupArtist(@PathVariable Long id) {
        makeupArtistService.deleteMakeupArtist(id);
        return "redirect:/makeup-artists";
    }*/
}
