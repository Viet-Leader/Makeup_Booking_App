package com.example.bookingmakeup.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // Trả về trang home.html
    }


    @GetMapping("/schedule")
    public ModelAndView schedulePage(HttpSession session) {
        // Kiểm tra xem session có chứa thông tin đăng nhập không
        if (session.getAttribute("user") == null) {
            return new ModelAndView("redirect:/login"); // Chuyển hướng nếu chưa đăng nhập
        }
        return new ModelAndView("redirect:/schedule.html/new"); // Hiển thị trang nếu đã đăng nhập
    }


    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Trả về trang register.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Trả về trang login.html
    }

}
