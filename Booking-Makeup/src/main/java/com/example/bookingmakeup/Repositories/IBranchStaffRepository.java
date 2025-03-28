package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.BranchStaff;
import com.example.bookingmakeup.Models.BranchStaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBranchStaffRepository extends JpaRepository<BranchStaff, BranchStaffId> {

}