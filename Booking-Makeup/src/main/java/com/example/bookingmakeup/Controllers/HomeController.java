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
    public String homePage(HttpSession session, Model model) {
        String message = (String) session.getAttribute("accessDeniedMessage");

        if (message != null) {
            model.addAttribute("accessDeniedMessage", message);
            session.removeAttribute("accessDeniedMessage"); // Xóa sau khi hiển thị
        }
        // Truyền user từ session vào model
        Object user = session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "home";
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
