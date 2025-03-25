package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.ServiceMakeUp;
import java.util.List;
import java.util.Optional;

public interface IServiceMakeUpService {
    List<ServiceMakeUp> getAllServices();
    Optional<ServiceMakeUp> getServiceById(Integer id);
    ServiceMakeUp createService(ServiceMakeUp service);
    ServiceMakeUp updateService(Integer id, ServiceMakeUp service);
    void deleteService(Integer id);
}
