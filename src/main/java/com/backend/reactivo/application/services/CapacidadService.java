package com.backend.reactivo.application.services;

import org.springframework.stereotype.Service;

import com.backend.reactivo.application.dto.ResponseCapacidad;
import com.backend.reactivo.domain.models.Capacidad;
import com.backend.reactivo.domain.ports.in.CreateCapacidadUseCase;
import com.backend.reactivo.domain.ports.in.RetrieveCapacidadUseCase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CapacidadService implements CreateCapacidadUseCase,RetrieveCapacidadUseCase{
	
	private final CreateCapacidadUseCase createTecnologiaUseCase;
	private final RetrieveCapacidadUseCase retrieveTecnologiaUseCase;
	
	public CapacidadService(CreateCapacidadUseCase createTecnologiaUseCase,
			RetrieveCapacidadUseCase retrieveTecnologiaUseCase) {
		this.createTecnologiaUseCase = createTecnologiaUseCase;
		this.retrieveTecnologiaUseCase = retrieveTecnologiaUseCase;
	}

	@Override
	public Mono<Capacidad> getCapacidad(Long id) {
		return retrieveTecnologiaUseCase.getCapacidad(id);
	}

	@Override
	public Mono<Capacidad> getCapacidadByNombre(String nombre) {
		return retrieveTecnologiaUseCase.getCapacidadByNombre(nombre);
	}

	@Override
	public Flux<Capacidad> getAllCapacidades() {
		return retrieveTecnologiaUseCase.getAllCapacidades();
	}

	@Override
	public Flux<Capacidad> getAllCapacidadesPag(int page, int size,String sortBy ,String direction) {
		return retrieveTecnologiaUseCase.getAllCapacidadesPag(page, size, sortBy,direction);
	}

	@Override
	public Mono<ResponseCapacidad> createCapacidad(Capacidad capacidad) {
		return createTecnologiaUseCase.createCapacidad(capacidad);
	}

}
