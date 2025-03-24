package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Services.IAccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    // ✅ Kiểm tra trạng thái đăng nhập
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> checkLoginStatus(HttpSession session) {
        Map<String, String> response = new HashMap<>();
        Account user = (Account) session.getAttribute("user");

        if (user != null) {
            response.put("status", "logged_in");
            response.put("email", user.getEmail());
            return ResponseEntity.ok(response);
        }

        response.put("status", "logged_out");
        return ResponseEntity.ok(response);
    }

    // ✅ Xử lý đăng nhập
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        String loginMessage = accountService.login(email, password);
        Map<String, String> response = new HashMap<>();

        if ("Đăng nhập thành công!".equals(loginMessage)) {
            Optional<Account> account = accountService.findByEmail(email);
            session.setAttribute("user", account.get()); // Lưu vào session

            response.put("status", "success");
            response.put("email", email);
            return ResponseEntity.ok(response);
        }

        response.put("status", "error");
        response.put("message", loginMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // ✅ Xử lý đăng ký
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Account account) {
        if (account.getRole() == null || account.getRole().isEmpty()) {
            account.setRole("customer");
        }
        String registerMessage = accountService.register(account);
        Map<String, String> response = new HashMap<>();

        if ("Đăng ký thành công!".equals(registerMessage)) {
            response.put("status", "success");
            response.put("message", registerMessage);
            return ResponseEntity.ok(response);
        }

        response.put("status", "error");
        response.put("message", registerMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ✅ Xử lý đăng xuất
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate(); // Xóa session
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Đăng xuất thành công!");
        return ResponseEntity.ok(response);
    }
}
