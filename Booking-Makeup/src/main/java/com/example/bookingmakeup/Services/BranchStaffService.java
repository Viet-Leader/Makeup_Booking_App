package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.BranchStaff;
import com.example.bookingmakeup.Models.BranchStaffId;
import com.example.bookingmakeup.Repositories.IBranchStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BranchStaffService implements IBranchStaffService {

    private final IBranchStaffRepository branchStaffRepository;

    @Autowired
    public BranchStaffService(IBranchStaffRepository branchStaffRepository) {
        this.branchStaffRepository = branchStaffRepository;
    }

    @Override
    public List<BranchStaff> getAllBranchStaff() {
        if (branchStaffRepository == null) {
            System.err.println("branchStaffRepository is null! Check Spring configuration.");
            return Collections.emptyList();
        }
        try {
            List<BranchStaff> staffList = branchStaffRepository.findAll();
            System.out.println("Staff List retrieved: " + staffList);
            return staffList != null ? staffList : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error fetching staff list: " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug dễ hơn
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<BranchStaff> getBranchStaffById(BranchStaffId id) {
        return branchStaffRepository.findById(id);
    }

    @Override
    public BranchStaff saveBranchStaff(BranchStaff branchStaff) {
        return branchStaffRepository.save(branchStaff);
    }

    @Override
    public void deleteBranchStaff(BranchStaffId id) {
        branchStaffRepository.deleteById(id);
    }
    public long getTotalBranchesStaff() {
        return branchStaffRepository.count();
    }
}