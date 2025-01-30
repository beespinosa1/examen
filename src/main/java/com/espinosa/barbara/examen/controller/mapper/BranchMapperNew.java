package com.espinosa.barbara.examen.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.espinosa.barbara.examen.controller.dto.BranchDTO;
import com.espinosa.barbara.examen.controller.dto.BranchHolidayDTO;
import com.espinosa.barbara.examen.model.Branch;
import com.espinosa.barbara.examen.model.BranchHoliday;

@Component
public class BranchMapperNew {

    public BranchDTO toDTO(Branch model) {
        if (model == null) {
            return null;
        }
        BranchDTO dto = new BranchDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setEmailAdress(model.getEmailAdress());
        dto.setPhoneNumber(model.getPhoneNumber());
        dto.setState(model.getState());
        dto.setCreationDate(model.getCreationDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        
        if (model.getBranchHolidays() != null) {
            List<BranchHolidayDTO> holidayDTOs = new ArrayList<>();
            for (BranchHoliday holiday : model.getBranchHolidays()) {
                holidayDTOs.add(toDTO(holiday));
            }
            dto.setBranchHolidays(holidayDTOs);
        }
        
        return dto;
    }

    public Branch toModel(BranchDTO dto) {
        if (dto == null) {
            return null;
        }
        Branch model = new Branch();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setEmailAdress(dto.getEmailAdress());
        model.setPhoneNumber(dto.getPhoneNumber());
        model.setState(dto.getState());
        model.setCreationDate(dto.getCreationDate());
        model.setLastModifiedDate(dto.getLastModifiedDate());
        
        if (dto.getBranchHolidays() != null) {
            List<BranchHoliday> holidays = new ArrayList<>();
            for (BranchHolidayDTO holidayDTO : dto.getBranchHolidays()) {
                holidays.add(toModel(holidayDTO));
            }
            model.setBranchHolidays(holidays);
        }
        
        return model;
    }

    public BranchHolidayDTO toDTO(BranchHoliday model) {
        if (model == null) {
            return null;
        }
        BranchHolidayDTO dto = new BranchHolidayDTO();
        dto.setDate(model.getDate());
        dto.setName(model.getName());
        return dto;
    }

    public BranchHoliday toModel(BranchHolidayDTO dto) {
        if (dto == null) {
            return null;
        }
        BranchHoliday model = new BranchHoliday();
        model.setDate(dto.getDate());
        model.setName(dto.getName());
        return model;
    }
}
