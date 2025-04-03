package com.example.bookingmakeup.Services;
import com.example.bookingmakeup.Models.Branch;

import java.util.List;

public interface IBranchesService {
    List<Branch> getAllBranches();
    Branch findBranchById(Integer id);

    Branch findBranchById(Long id);

    Branch findBranchByName(String name);
    Branch saveBranch(Branch branch);
    Branch updateBranch(Integer id, Branch branch);
    void deleteBranch(Integer id);
    boolean branchExists(String name);

    Branch updateBranch(Long id, Branch branch);

    void deleteBranch(Long id);

    long getTotalBranches();
}
