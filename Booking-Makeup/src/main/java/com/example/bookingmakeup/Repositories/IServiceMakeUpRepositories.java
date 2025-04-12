package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.ServiceMakeUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceMakeUpRepositories extends JpaRepository<ServiceMakeUp, Long> {
    ServiceMakeUp findByServiceId(int serviceId);
    List<ServiceMakeUp> findAll();
}
