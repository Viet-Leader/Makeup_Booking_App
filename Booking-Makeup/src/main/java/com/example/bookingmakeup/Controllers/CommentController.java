package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Comment;
import com.example.bookingmakeup.Models.Customer;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Services.ICommentService;
import com.example.bookingmakeup.Services.ICustomerService;
import com.example.bookingmakeup.Services.IMakeupArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IMakeupArtistService makeupArtistService;

    @Autowired
    private ICustomerService customerService;



    // 🔹 Thêm bình luận mới
    @PostMapping("/add")
    @ResponseBody
    public Comment addComment(
            @RequestParam("makeupArtistId") Long makeupArtistId,
            @RequestParam("cmt") String cmt,
            HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            throw new RuntimeException("Bạn chưa đăng nhập.");
        }

        Optional<MakeupArtist> artistOpt = makeupArtistService.getMakeupArtistByMakeupArtistId(makeupArtistId);
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId);

        if (artistOpt.isPresent() && customerOpt.isPresent()) {
            Comment comment = new Comment();
            comment.setCmt(cmt);
            comment.setMakeupArtist(artistOpt.get());
            comment.setCustomer(customerOpt.get());
            return commentService.saveComment(comment);
        }
        throw new RuntimeException("Không thể thêm bình luận.");
    }




    // 🔹 Xóa bình luận (chỉ cho phép người đăng hoặc admin xóa)
    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, HttpSession session) {
        // Lấy ID người dùng từ session
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "/login"; // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
        }

        // Lấy comment từ database
        Optional<Comment> commentOpt = commentService.getCommentById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();

            // Kiểm tra comment có khách hàng không
            if (comment.getCustomer() == null || comment.getCustomer().getCustomerId() == null) {
                throw new RuntimeException("Bình luận không có thông tin khách hàng.");
            }

            // Kiểm tra quyền xóa: chỉ chủ sở hữu hoặc admin mới được xóa
            if (comment.getCustomer().getCustomerId().equals(customerId) || session.getAttribute("isAdmin") != null) {
                commentService.deleteCommentById(commentId);
                return "/makeup-artists/" + comment.getMakeupArtist().getMakeupArtistId();
            }
        }
        return "/makeup-artists"; // Nếu không tìm thấy bình luận, quay lại trang danh sách
    }

}
