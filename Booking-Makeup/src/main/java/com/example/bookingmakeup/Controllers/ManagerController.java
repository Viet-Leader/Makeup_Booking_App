package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Services.IAccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private IAccountService accountService;
    @GetMapping("/index")
    public String indexPage() {
        return "manager/index";
    }
    @GetMapping("/appointment")
    public String appointmentPage() {
        return "manager/appointment";
    }
    @GetMapping("/staff")
    public String staffPage() {
        return "manager/staff";
    }

    @GetMapping("/infor")
    public String getManagerInfor(Model model, HttpSession session) {
        // 🔍 Lấy userId từ session
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("🔍 DEBUG - userId trong session: " + userId);

        if (userId == null) {
            System.out.println("❌ Không tìm thấy userId, chuyển về login!");
            return "redirect:/login"; // Nếu chưa đăng nhập, yêu cầu đăng nhập lại
        }

        // ✅ Tìm kiếm tài khoản theo userId
        Account loggedInAccount = accountService.findById(userId).orElse(null);
        System.out.println("✅ DEBUG - Tài khoản tìm thấy: " + loggedInAccount);

        if (loggedInAccount == null) {
            System.out.println("❌ Không tìm thấy tài khoản với userId = " + userId);
            return "redirect:/login"; // Nếu tài khoản không tồn tại, quay về login
        }

        model.addAttribute("loggedInAccount", loggedInAccount);
        return "manager/infor";
    }

    // ✅ Xử lý cập nhật tài khoản
    @PostMapping("/updateAccount")
    public String updateAccount(@RequestParam Long userId,
                                @RequestParam String nameAccount,
                                @RequestParam String phone,
                                @RequestParam String address) {
        Account account = accountService.findById(userId).orElse(null);
        if (account == null) {
            return "redirect:/manager/infor"; // Không tìm thấy tài khoản
        }

        account.setNameAccount(nameAccount);
        account.setPhone(phone);
        account.setAddress(address);
        accountService.update(account); // Cập nhật tài khoản

        return "redirect:/manager/infor?success=true"; // Quay lại trang với thông báo thành công
    }
}
