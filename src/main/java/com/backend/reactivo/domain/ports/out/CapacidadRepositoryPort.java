package com.backend.reactivo.domain.ports.out;

import org.springframework.data.domain.Pageable;

import com.backend.reactivo.domain.models.Capacidad;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CapacidadRepositoryPort {

	Mono<Capacidad> save(Capacidad tecnologia);
	
	Mono<Capacidad> findById(Long id);
	
	Flux<Capacidad> findAll();
	
	Mono<Capacidad> findByNombre(String nombre);
	
	Flux<Capacidad> findAllPag(Pageable pageable);

}
