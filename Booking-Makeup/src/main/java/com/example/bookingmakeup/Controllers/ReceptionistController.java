package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {

    @Autowired
    private IAccountRepository accountRepository;

    @GetMapping("")
    public String receptionistIndex() {
        return "receptionist/home";
    }

    @GetMapping("/home.html")
    public String receptionistHome() {
        return "receptionist/home";
    }

    @GetMapping("/customer.html")
    public String receptionistCustomer() {
        return "receptionist/customer";
    }

    @GetMapping("/checkin.html")
    public String receptionistCheckin() {
        return "receptionist/checkin";
    }

    @GetMapping("/appointment.html")
    public String receptionistAppointment() {
        return "receptionist/appointment";
    }

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        List<Account> customers = accountRepository.findAll();
        model.addAttribute("customers", customers);
        return "customer"; // Trả về customer.html
    }
}
