package com.backend.reactivo.domain.ports.out;

import com.backend.reactivo.domain.models.CapacidadTecnologia;

import reactor.core.publisher.Mono;

public interface CapacidadTecnologiaRepositoryPort {

	Mono<CapacidadTecnologia> save(CapacidadTecnologia capacidadTecnologia);
	
}
