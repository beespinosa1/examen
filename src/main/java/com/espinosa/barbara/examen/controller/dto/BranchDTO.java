package com.espinosa.barbara.examen.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchDTO {
    private String id;
    private String name;
    private String emailAdress;
    private String phoneNumber;
    private String state;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private List<BranchHolidayDTO> branchHolidays;

    @Data
    @NoArgsConstructor
    public static class BranchHolidayDTO {
        private LocalDate date;
        private String name;
    }
}
