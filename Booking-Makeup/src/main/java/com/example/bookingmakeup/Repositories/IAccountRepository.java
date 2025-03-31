package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    List<Account> findByBranch_BranchId(Long branchId);
}
