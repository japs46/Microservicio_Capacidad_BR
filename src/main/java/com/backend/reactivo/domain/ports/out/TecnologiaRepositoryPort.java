package com.backend.reactivo.domain.ports.out;

import com.backend.reactivo.domain.models.Tecnologia;

import reactor.core.publisher.Flux;

public interface TecnologiaRepositoryPort {

	Flux<Tecnologia> getTecnologiasByCapacidad(Long capacidadId);
}
