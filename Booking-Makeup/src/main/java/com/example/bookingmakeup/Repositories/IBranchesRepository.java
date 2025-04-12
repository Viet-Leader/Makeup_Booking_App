package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBranchesRepository extends JpaRepository<Branch, Long> {
    // Custom query methods can be added here if needed
    Branch findBranchByName(String name);
    boolean existsByName(String name);
    List<Branch> findAllByBranchId(Long branchId);

    Branch findByName(String name);
}