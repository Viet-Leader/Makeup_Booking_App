package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Models.ServiceMakeUp;
import com.example.bookingmakeup.Repositories.IServiceMakeUpRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceMakeUpService implements IServiceMakeUpService {

    @Autowired
    private IServiceMakeUpRepositories serviceMakeUpRepositories;

    @Override
    public List<ServiceMakeUp> getAllServices() {
        return serviceMakeUpRepositories.findAll();
    }

    @Override
    public Optional<ServiceMakeUp> getServiceById(long id) {
        return serviceMakeUpRepositories.findById(id);
    }

    @Override
    public ServiceMakeUp createService(ServiceMakeUp service) {
        return serviceMakeUpRepositories.save(service);
    }

    @Override
    public ServiceMakeUp updateService(long id, ServiceMakeUp service) {
        if(serviceMakeUpRepositories.existsById(id)) {
            service.setServiceId(id);
            return serviceMakeUpRepositories.save(service);
        }
        return null;
    }

    @Override
    public void deleteService(long id) {
        serviceMakeUpRepositories.deleteById(id);
    }

    @Override
    public ServiceMakeUp saveService(ServiceMakeUp service) {
        return serviceMakeUpRepositories.save(service);
    }
    public long getTotalSevice() {
        return serviceMakeUpRepositories.count();
    }

}
