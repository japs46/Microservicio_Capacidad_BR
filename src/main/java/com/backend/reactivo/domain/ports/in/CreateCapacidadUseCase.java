package com.backend.reactivo.domain.ports.in;

import org.springframework.stereotype.Component;

import com.backend.reactivo.application.dto.ResponseCapacidad;
import com.backend.reactivo.domain.models.Capacidad;

import reactor.core.publisher.Mono;

@Component
public interface CreateCapacidadUseCase {

	Mono<ResponseCapacidad> createCapacidad(Capacidad capacidad);
}
