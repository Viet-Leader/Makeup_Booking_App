package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.ServiceMakeUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceMakeUpRepositories extends JpaRepository<ServiceMakeUp, Integer> {
    ServiceMakeUp findByServiceId(int serviceId);
}
