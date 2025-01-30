package com.espinosa.barbara.examen.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.espinosa.barbara.examen.controller.dto.BranchDTO;
import com.espinosa.barbara.examen.model.Branch;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
	unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BranchMapperNew {

    public BranchDTO toDTO(Branch model);
	Branch toModel(BranchDTO dto);
}
