package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Optional<Customer> getCustomerById(Long customerId);
    Optional<Customer> getCustomerByUserId(Long userId);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Long customerId);
    List<Customer> getAllCustomers();
}
