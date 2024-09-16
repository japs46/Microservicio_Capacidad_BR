package com.backend.reactivo.domain.ports.in;

import org.springframework.stereotype.Component;

import com.backend.reactivo.domain.models.Capacidad;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface RetrieveCapacidadUseCase {

	Mono<Capacidad> getCapacidad(Long id);
	
	Mono<Capacidad> getCapacidadByNombre(String nombre);
	
	Flux<Capacidad> getAllCapacidades();
	
	Flux<Capacidad> getAllCapacidadesPag(int page, int size, String sortBy,String direction);
}
