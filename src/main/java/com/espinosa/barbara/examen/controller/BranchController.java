package com.espinosa.barbara.examen.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.espinosa.barbara.examen.controller.dto.BranchDTO;
import com.espinosa.barbara.examen.controller.dto.BranchHolidayDTO;
import com.espinosa.barbara.examen.controller.mapper.BranchMapperNew;
import com.espinosa.barbara.examen.model.Branch;
import com.espinosa.barbara.examen.model.BranchHoliday;
import com.espinosa.barbara.examen.service.BranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/branches")
@Slf4j
public class BranchController {

    private final BranchService service;
    private final BranchMapperNew mapper;

    public BranchController(BranchService service, BranchMapperNew mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Listar todas las sucursales", description = "Devuelve una lista de todas las sucursales registradas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        log.info("Iniciando obtención de todas las sucursales");
        try {
            List<Branch> branches = this.service.findAll();
            List<BranchDTO> dtos = new ArrayList<>(branches.size());
            for (Branch branch : branches) {
                dtos.add(mapper.toDTO(branch));
            }
            log.info("Se encontraron {} sucursales", branches.size());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error al obtener todas las sucursales", e);
            throw e;
        }
    }

    @Operation(summary = "Obtener una sucursal por su ID", description = "Devuelve los detalles de una sucursal específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(
            @Parameter(description = "ID de la sucursal a buscar", required = true) 
            @PathVariable("id") String id) {
        log.info("Buscando sucursal con ID: {}", id);
        try {
            Branch branch = this.service.findById(id);
            if (branch == null) {
                log.warn("No se encontró la sucursal con ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            log.info("Sucursal encontrada con ID: {}", id);
            return ResponseEntity.ok(mapper.toDTO(branch));
        } catch (Exception e) {
            log.error("Error al buscar sucursal con ID: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Crear una nueva sucursal", description = "Permite registrar una nueva sucursal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal creada exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class)))
    })
    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO dto) {
        log.info("Iniciando creación de nueva sucursal");
        try {
            Branch branch = this.service.create(mapper.toModel(dto));
            log.info("Sucursal creada exitosamente con ID: {}", branch.getId());
            return ResponseEntity.ok(mapper.toDTO(branch));
        } catch (Exception e) {
            log.error("Error al crear nueva sucursal", e);
            throw e;
        }
    }

    @Operation(summary = "Actualizar una sucursal existente", description = "Permite modificar los datos de una sucursal específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @Parameter(description = "ID de la sucursal a actualizar", required = true)
            @PathVariable("id") String id, 
            @RequestBody BranchDTO dto) {
        log.info("Iniciando actualización de sucursal con ID: {}", id);
        try {
            Branch branch = this.service.update(id, mapper.toModel(dto));
            if (branch == null) {
                log.warn("No se encontró la sucursal a actualizar con ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            log.info("Sucursal actualizada exitosamente con ID: {}", id);
            return ResponseEntity.ok(mapper.toDTO(branch));
        } catch (Exception e) {
            log.error("Error al actualizar sucursal con ID: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Listar feriados de una sucursal", description = "Obtiene todos los feriados de una sucursal específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de feriados obtenida exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchHolidayDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @GetMapping("/holidays/{id}")
    public ResponseEntity<List<BranchHolidayDTO>> getHolidays(
            @Parameter(description = "ID de la sucursal", required = true)
            @PathVariable("id") String id) {
        log.info("Obteniendo feriados para sucursal con ID: {}", id);
        try {
            List<BranchHoliday> holidays = this.service.findHolidays(id);
            List<BranchHolidayDTO> dtos = new ArrayList<>(holidays.size());
            for (BranchHoliday holiday : holidays) {
                dtos.add(mapper.toDTO(holiday));
            }
            log.info("Se encontraron {} feriados para la sucursal con ID: {}", holidays.size(), id);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error al obtener feriados para sucursal con ID: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Crear un nuevo feriado", description = "Agrega un nuevo feriado a una sucursal específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feriado creado exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchHolidayDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @PostMapping("/holidays/{id}")
    public ResponseEntity<BranchHolidayDTO> createHoliday(
            @Parameter(description = "ID de la sucursal", required = true)
            @PathVariable("id") String id, 
            @RequestBody BranchHolidayDTO dto) {
        log.info("Creando nuevo feriado para sucursal con ID: {}", id);
        try {
            BranchHoliday holiday = this.service.createHoliday(id, mapper.toModel(dto));
            if (holiday == null) {
                log.warn("No se pudo crear el feriado, sucursal no encontrada con ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            log.info("Feriado creado exitosamente para sucursal con ID: {}", id);
            return ResponseEntity.ok(mapper.toDTO(holiday));
        } catch (Exception e) {
            log.error("Error al crear feriado para sucursal con ID: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Eliminar un feriado", description = "Elimina un feriado de una sucursal específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Feriado eliminado exitosamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(
            @Parameter(description = "ID de la sucursal", required = true)
            @PathVariable("id") String id, 
            @RequestBody BranchHolidayDTO dto) {
        log.info("Eliminando feriado para sucursal con ID: {}", id);
        try {
            this.service.deleteHoliday(id, mapper.toModel(dto));
            log.info("Feriado eliminado exitosamente para sucursal con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar feriado para sucursal con ID: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Obtener un feriado por fecha", description = "Busca un feriado específico por su fecha en una sucursal.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feriado encontrado", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchHolidayDTO.class))),
        @ApiResponse(responseCode = "404", description = "Feriado no encontrado", content = @Content)
    })
    @GetMapping("/holidays/{id}/{date}")
    public ResponseEntity<BranchHolidayDTO> getHoliday(
            @Parameter(description = "ID de la sucursal", required = true)
            @PathVariable("id") String id, 
            @Parameter(description = "Fecha del feriado (YYYY-MM-DD)", required = true)
            @PathVariable("date") LocalDate date) {
        log.info("Buscando feriado para fecha {} en sucursal con ID: {}", date, id);
        try {
            BranchHoliday holiday = this.service.findHoliday(id, date);
            if (holiday == null) {
                log.warn("No se encontró feriado para fecha {} en sucursal con ID: {}", date, id);
                return ResponseEntity.notFound().build();
            }
            log.info("Feriado encontrado para fecha {} en sucursal con ID: {}", date, id);
            return ResponseEntity.ok(mapper.toDTO(holiday));
        } catch (Exception e) {
            log.error("Error al buscar feriado para fecha {} en sucursal con ID: {}", date, id, e);
            throw e;
        }
    }
}
