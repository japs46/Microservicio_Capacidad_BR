package com.backend.reactivo.infrastructure.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.reactivo.infrastructure.entities.CapacidadEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CapacidadEntityRepository extends ReactiveCrudRepository<CapacidadEntity, Long>{

	Mono<CapacidadEntity> findByNombre(String nombre);
	
	Flux<CapacidadEntity> findAllBy(Pageable pageable);
	
}
