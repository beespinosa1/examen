package com.espinosa.barbara.examen.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.espinosa.barbara.examen.exception.NotFoundException;
import com.espinosa.barbara.examen.model.Branch;
import com.espinosa.barbara.examen.model.BranchHoliday;
import com.espinosa.barbara.examen.repository.BranchRepository;

@Service
public class BranchService {

    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    public List<Branch> findAll() {
        return repository.findAll();
    }

    public Branch findById(String id) {
        
        Branch branch = repository.findById(id).orElse(null);
        if (branch == null) {
            throw new NotFoundException(id, "Branch");
        } else {
            return branch;
        }
    }

    public Branch create(Branch branch) {
        branch.setCreationDate(LocalDateTime.now());
        branch.setLastModifiedDate(LocalDateTime.now());
        return repository.save(branch);
    }

    public Branch update(String id, Branch branch) {
        Branch branchDB = this.findById(id);
        if (branchDB == null) {
            return null;
        }
        branch.setId(id);
        branch.setLastModifiedDate(LocalDateTime.now());
        return repository.save(branch);
    }

    public List<BranchHoliday> findHolidays(String id) {
        Branch branch = this.findById(id);
        if (branch == null) {
            throw new NotFoundException(id, "Branch Holiday");
        }else{
            return branch.getBranchHolidays();
        }
        
    }

    public BranchHoliday createHoliday(String id, BranchHoliday holiday) {
        Branch branch = this.findById(id);
        if (branch == null) {
            return null;
        }
        if (branch.getBranchHolidays() == null) {
            branch.setBranchHolidays(new ArrayList<>());
        }
        branch.getBranchHolidays().add(holiday);
        branch.setLastModifiedDate(LocalDateTime.now());
        repository.save(branch);
        return holiday;
    }

    public void deleteHoliday(String id, BranchHoliday holiday) {
        Branch branch = this.findById(id);
        if (branch != null && branch.getBranchHolidays() != null) {
            branch.getBranchHolidays().removeIf(h -> 
                h.getDate().equals(holiday.getDate()) && 
                h.getName().equals(holiday.getName()));
            branch.setLastModifiedDate(LocalDateTime.now());
            repository.save(branch);
        }
    }

    public BranchHoliday findHoliday(String id, LocalDate date) {
        Branch branch = this.findById(id);
        if (branch == null || branch.getBranchHolidays() == null) {
            return null;
        }
        return branch.getBranchHolidays().stream()
                .filter(h -> h.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }
}
