package com.espinosa.barbara.examen.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Branch {

    private String id;
    private String emailAdress;
    private String name;
    private String phoneNumber;
    private String state;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private List<BranchHoliday> branchHolidays;

    @Data
    @NoArgsConstructor
    public static class BranchHoliday {
        private LocalDate date;
        private String name;
    }

}
