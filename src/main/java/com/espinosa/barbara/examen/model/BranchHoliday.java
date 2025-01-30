package com.espinosa.barbara.examen.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "branch_holidays")
public class BranchHoliday {
    private LocalDate date;
    private String name;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}