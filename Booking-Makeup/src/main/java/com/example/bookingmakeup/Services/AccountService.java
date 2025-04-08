package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Repositories.IAccountRepository;
import com.example.bookingmakeup.Services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public String register(Account account) {
        Optional<Account> existingAccount = accountRepository.findByEmail(account.getEmail());
        if (existingAccount.isPresent()) {
            return "Email đã tồn tại!";
        }
        accountRepository.save(account);
        return "Đăng ký thành công!";
    }

    @Override
    public String login(String email, String password) {
        Optional<Account> account = accountRepository.findByEmail(email);

        // Debug: In ra account có tồn tại không
        System.out.println("Tìm thấy tài khoản: " + account.isPresent());

        if (account.isPresent()) {
            // Debug: In ra password từ DB
            System.out.println("Password trong DB: " + account.get().getPassword());

            if (account.get().getPassword().equals(password)) {
                return "Đăng nhập thành công!";
            } else {
                return "Mật khẩu không đúng!";
            }
        }
        return "Email không tồn tại!";
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

}
