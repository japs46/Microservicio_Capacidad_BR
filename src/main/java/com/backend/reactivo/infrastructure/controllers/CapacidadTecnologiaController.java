package com.backend.reactivo.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.reactivo.infrastructure.entities.CapacidadTecnologiaEntity;
import com.backend.reactivo.infrastructure.repositories.CapacidadTecnologiaEntityRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/captec")
public class CapacidadTecnologiaController {
	
	@Autowired
	private CapacidadTecnologiaEntityRepository capacidadTecnologiaRepository;

	@PostMapping("/guardar")
	public Mono<ResponseEntity<CapacidadTecnologiaEntity>> prueba(@RequestBody CapacidadTecnologiaEntity capacidadTecnologiaEntity){
		
		return capacidadTecnologiaRepository.save(capacidadTecnologiaEntity)
				.map(capacidad ->{
					return new ResponseEntity<>(capacidad,HttpStatus.OK);
				});
	}
}
