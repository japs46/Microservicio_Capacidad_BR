package com.backend.reactivo.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.reactivo.application.dto.ResponseCapacidad;
import com.backend.reactivo.application.services.CapacidadService;
import com.backend.reactivo.domain.models.Capacidad;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/capacidades")
public class CapacidadController {

	private final CapacidadService capacidadService;

	public CapacidadController(CapacidadService capacidadService) {
		this.capacidadService = capacidadService;
	}
	
    @Operation(summary = "Crear una nueva capacidad", description = "Guarda una nueva capacidad en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Capacidad guardada exitosamente")
    @ApiResponse(responseCode = "406", description = "No se aceptó la solicitud")
	@PostMapping("/guardar")
	public Mono<ResponseEntity<ResponseCapacidad>> crearCapacidad(@RequestBody Capacidad capacidad){
    	return capacidadService.createCapacidad(capacidad)
    			.map(responseCapacidad-> {
    				if (responseCapacidad.isStatus()) {
    					return ResponseEntity.status(HttpStatus.CREATED).body(responseCapacidad);
					}
    				 return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseCapacidad);
    			})
    			.defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
	}
	

    @Operation(summary = "Obtener Capacidad por ID", description = "Recupera una capacidad por su ID.")
    @ApiResponse(responseCode = "200", description = "Capacidad encontrada")
    @ApiResponse(responseCode = "404", description = "Capacidad no encontrada")
	@GetMapping("/obtener/{id}")
	public Mono<ResponseEntity<Capacidad>> obtenerCapacidad(@PathVariable Long id){
		return capacidadService.getCapacidad(id)
				.map(capacidad -> ResponseEntity.ok(capacidad))
				.defaultIfEmpty(new ResponseEntity<Capacidad>(HttpStatus.NOT_FOUND));
	}
	
    @Operation(summary = "Obtener todas las capacidades", description = "Recupera todas las capacidades disponibles.")
    @ApiResponse(responseCode = "200", description = "Lista de capacidades")
	@GetMapping("/obtener")
	public Flux<Capacidad> obtenerCapacidades(){
		return capacidadService.getAllCapacidades();
	}
    
    @GetMapping("/obtenerPag")
    public Flux<Capacidad> obtenerCapacidadesPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return capacidadService.getAllCapacidadesPag(page, size, sortBy,direction);
    }
}
