package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Models.ServiceMakeUp;
import java.util.List;
import java.util.Optional;

public interface IServiceMakeUpService {
    List<ServiceMakeUp> getAllServices();
    Optional<ServiceMakeUp> getServiceById(long id);
    ServiceMakeUp createService(ServiceMakeUp service);
    ServiceMakeUp updateService(long id, ServiceMakeUp service);
    void deleteService(long id);
    ServiceMakeUp saveService(ServiceMakeUp service);
    long getTotalSevice();

}
