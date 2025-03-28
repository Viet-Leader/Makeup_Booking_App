package com.example.bookingmakeup.Controllers;

import com.example.bookingmakeup.Models.ServiceMakeUp;
import com.example.bookingmakeup.Services.IServiceMakeUpService;
import com.example.bookingmakeup.Services.ServiceMakeUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/services")
public class ServiceMakeUpController {
    @Autowired
    private IServiceMakeUpService serviceMakeUpService;

    @GetMapping
    public String getServicesPage(Model model) {
        List<ServiceMakeUp> services = serviceMakeUpService.getAllServices();
        model.addAttribute("services", services);
        return "services"; // Trả về file services.html
    }

    @GetMapping("/api")
    @ResponseBody
    public List<ServiceMakeUp> getAllServices() {
        return serviceMakeUpService.getAllServices();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public Optional<ServiceMakeUp> getServiceById(@PathVariable Integer id) {
        return serviceMakeUpService.getServiceById(id);
    }

    @PostMapping("/api")
    @ResponseBody
    public ServiceMakeUp createService(@RequestBody ServiceMakeUp service) {
        return serviceMakeUpService.createService(service);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public Optional<ServiceMakeUp> updateService(@PathVariable Integer id, @RequestBody ServiceMakeUp service) {
        return Optional.ofNullable(serviceMakeUpService.updateService(id, service));
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public void deleteService(@PathVariable Integer id) {
        serviceMakeUpService.deleteService(id);
    }
}
