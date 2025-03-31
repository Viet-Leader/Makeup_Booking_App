package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Account;
import com.example.bookingmakeup.Models.Customer;
import com.example.bookingmakeup.Repositories.IAccountRepository;
import com.example.bookingmakeup.Repositories.ICustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    @Transactional
    public String register(Account account) {
        Optional<Account> existingAccount = accountRepository.findByEmail(account.getEmail());
        if (existingAccount.isPresent()) {
            return "Email đã tồn tại!";
        }

        // ✅ Lưu tài khoản vào bảng account trước
        account = accountRepository.save(account);

        // ✅ Nếu tài khoản có role "customer", kiểm tra trong bảng customers
        if ("customer".equalsIgnoreCase(account.getRole())) {
            // 🔹 Truy vấn theo user_id thay vì account object
            Optional<Customer> existingCustomer = customerRepository.findById(account.getUserId());

            if (existingCustomer.isEmpty()) {
                Customer customer = new Customer();
                customer.setUser(account);
                customerRepository.save(customer);
            }
        }

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

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }
    @Override
    public Optional<Account> findById(Long userId) {
        return accountRepository.findById(userId);
    }

    @Override
    public void update(Account account) {
        accountRepository.save(account); // Cập nhật thông tin
    }

    @Override
    public List<Account> getAccountsByBranch(Long userId) {
        Optional<Account> account = accountRepository.findById(userId);
        if (account.isPresent() && account.get().getBranch() != null) {
            Long branchId = account.get().getBranch().getBranchId();
            return accountRepository.findByBranch_BranchId(branchId);
        }
        return Collections.emptyList();
    }
}
