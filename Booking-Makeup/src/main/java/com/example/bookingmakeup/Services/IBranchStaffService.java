package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.BranchStaff;
import com.example.bookingmakeup.Models.BranchStaffId;
import java.util.List;
import java.util.Optional;

public interface IBranchStaffService {
    List<BranchStaff> getAllBranchStaff();
    Optional<BranchStaff> getBranchStaffById(BranchStaffId id);
    BranchStaff saveBranchStaff(BranchStaff branchStaff);
    void deleteBranchStaff(BranchStaffId id);
}