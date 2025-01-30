package com.espinosa.barbara.examen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.espinosa.barbara.examen.controller.dto.BranchDTO;
import com.espinosa.barbara.examen.controller.mapper.BranchMapperNew;
import com.espinosa.barbara.examen.model.Branch;
import com.espinosa.barbara.examen.service.BranchService;

import io.micrometer.core.ipc.http.HttpSender.Response;

@RestController
@RequestMapping("/v1/branches")
public class BranchController {

    private final BranchService service;
    private final BranchMapperNew mapper;

    public BranchController(BranchService service, BranchMapperNew mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        List<Branch> branches = this.service.findAll();

        List<BranchDTO> dtos = new ArrayList<>(branches.size());
        for (Branch branch : branches) {
            dtos.add(mapper.toDTO(branch));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable("id") String id) {
        Branch branch = this.service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(branch));
    }


}
