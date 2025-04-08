package com.example.bookingmakeup.Services;


import com.example.bookingmakeup.Models.Branch;
import com.example.bookingmakeup.Repositories.IBranchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class BranchesService implements IBranchesService {
    @Override
    public void deleteBranch(Integer id) {

    }

    @Override
    public boolean branchExists(String name) {
        return false;
    }

    @Autowired
    private IBranchesRepository branchRepository;

    @Override
    public List<Branch> getAllBranches() {
        if (branchRepository == null) {
            System.err.println("branchStaffRepository is null! Check Spring configuration.");
            return Collections.emptyList();
        }
        try {
            List<Branch> branchList = branchRepository.findAll();
            System.out.println("Staff List retrieved: " + branchList);
            return branchList != null ? branchList : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error fetching staff list: " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug dễ hơn
            return Collections.emptyList();
        }
    }

    @Override
    public Branch findBranchById(Long id) {
        // Kiểm tra tham số đầu vào
        if (id == null) {
            throw new IllegalArgumentException("ID của chi nhánh không được null");
        }

        try {
            Optional<Branch> branch = branchRepository.findById(id);
            if (branch.isEmpty()) {
                // Log để debug
                System.out.println("Không tìm thấy chi nhánh với ID: " + id);
            }
            return branch.orElse(null);
        } catch (Exception e) {
            // Log lỗi để debug
            System.err.println("Lỗi khi tìm chi nhánh với ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Không thể tìm chi nhánh: " + e.getMessage(), e);
        }
    }

    @Override
    public Branch findBranchByName(String name) {
        return branchRepository.findByName(name);
    }

    @Override
    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Integer id, Branch branch) {
        return null;
    }

    @Override
    public Branch updateBranch(Long id, Branch branch) {
        if (branchRepository.existsById(id)) {
            branch.setBranchId(id);
            return branchRepository.save(branch);
        }
        return null;
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }

    public long getTotalBranches() {
        return branchRepository.count();
    }
}


