package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.Customer;
import com.example.bookingmakeup.Repositories.ICustomerRepository;
import com.example.bookingmakeup.Services.CustomerService;
import com.example.bookingmakeup.Services.IAccountService;
import com.example.bookingmakeup.Services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("")
    public String receptionistIndex() {
        return "receptionist/home";
    }

    @GetMapping("/home.html")
    public String receptionistHome() {
        return "receptionist/home";
    }

    @GetMapping("/customer.html")
    public String receptionistCustomer(Model model) {
        List<Account> customers = accountService.getAllCustomers(); // 🔹 Lấy danh sách khách hàng
        model.addAttribute("customers", customers); // 🔹 Thêm vào model

        return "receptionist/customer"; // ✅ Phải khớp với tên file trong `templates/receptionist/`
    }

    @GetMapping("/checkin.html")
    public String receptionistCheckin() {
        return "receptionist/checkin";
    }

    @GetMapping("/appointment.html")
    public String receptionistAppointment() {
        return "receptionist/appointment";
    }

}
