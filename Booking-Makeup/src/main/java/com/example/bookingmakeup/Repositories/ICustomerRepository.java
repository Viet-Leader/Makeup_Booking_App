package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser_UserId(Long userId); // Dùng "user" thay vì "userId"
    Optional<Customer> findByUser(Account user);
    Optional<Customer> findById(Long customerId);
}

