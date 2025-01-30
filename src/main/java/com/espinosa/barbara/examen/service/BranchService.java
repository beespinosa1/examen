package com.espinosa.barbara.examen.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.espinosa.barbara.examen.model.Branch;

@Service
public class BranchService {

    public List<Branch> findAll() {
        List<Branch> branches = new ArrayList<>();
        Branch br = new Branch();
        br.setId("1");
        br.setEmailAdress("sucursaluno@gmail.com");
        br.setName("Sucursal Uno");
        br.setPhoneNumber("26663006");
        br.setState("Active");
        br.setCreationDate(LocalDateTime.now().minusYears(1));
        br.setLastModifiedDate(LocalDateTime.now()); 

        List<Branch.BranchHoliday> holidays = new ArrayList<>();
        Branch.BranchHoliday holiday = new Branch.BranchHoliday();
        holiday.setDate(LocalDate.of(2024, 12, 25));
        holiday.setName("Christmas");
        holidays.add(holiday);

        br.setBranchHolidays(holidays); 
        branches.add(br);

        return branches;

    }

}
