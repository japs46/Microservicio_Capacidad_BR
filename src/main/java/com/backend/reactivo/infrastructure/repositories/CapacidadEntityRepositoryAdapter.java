package com.backend.reactivo.infrastructure.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.backend.reactivo.domain.models.Capacidad;
import com.backend.reactivo.domain.ports.out.CapacidadRepositoryPort;
import com.backend.reactivo.infrastructure.entities.CapacidadEntity;
import com.backend.reactivo.infrastructure.mappers.CapacidadMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CapacidadEntityRepositoryAdapter implements CapacidadRepositoryPort{
	
	private final CapacidadEntityRepository capacidadEntityRepository;

	public CapacidadEntityRepositoryAdapter(CapacidadEntityRepository capacidadEntityRepository) {
		this.capacidadEntityRepository = capacidadEntityRepository;
	}

	@Override
	public Mono<Capacidad> save(Capacidad capacidad) {
		
		CapacidadEntity capacidadEntity = CapacidadMapper.toEntity(capacidad);
		
		Mono<Capacidad> monoTecnologia = CapacidadMapper.toMonoDomain(capacidadEntityRepository.save(capacidadEntity));
		
		return monoTecnologia;
	}

	@Override
	public Mono<Capacidad> findById(Long id) {
		return CapacidadMapper.toMonoDomain(capacidadEntityRepository.findById(id));
	}

	@Override
	public Flux<Capacidad> findAll() {
		return capacidadEntityRepository.findAll().map(CapacidadMapper::toDomain);
	}

	@Override
	public Mono<Capacidad> findByNombre(String nombre) {
		return capacidadEntityRepository.findByNombre(nombre).map(CapacidadMapper::toDomain);
	}

	@Override
	public Flux<Capacidad> findAllPag(Pageable pageable) {
		return capacidadEntityRepository.findAllBy(pageable).map(CapacidadMapper::toDomain);
	}

}
