package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBranchRepository extends JpaRepository<Branch, Integer> {
}