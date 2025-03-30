package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    String register(Account account);
    String login(String email, String password);

    Optional<Account> findByEmail(String email);
    List<Account> getAllAccounts();
    Optional<Account> findById(Long userId);
    void update(Account account);
}
