package com.backend.reactivo.infrastructure.repositories;

import org.springframework.stereotype.Component;

import com.backend.reactivo.domain.models.CapacidadTecnologia;
import com.backend.reactivo.domain.ports.out.CapacidadTecnologiaRepositoryPort;
import com.backend.reactivo.infrastructure.mappers.CapacidadTecnologiaMapper;

import reactor.core.publisher.Mono;

@Component
public class CapacidadTecnologiaRepositoryEntityAdapter implements CapacidadTecnologiaRepositoryPort{
	
	private final CapacidadTecnologiaEntityRepository capacidadTecnologiaRepository;

	public CapacidadTecnologiaRepositoryEntityAdapter(CapacidadTecnologiaEntityRepository capacidadTecnologiaRepository) {
		this.capacidadTecnologiaRepository = capacidadTecnologiaRepository;
	}

	@Override
	public Mono<CapacidadTecnologia> save(CapacidadTecnologia capacidadTecnologia) {
		
		return capacidadTecnologiaRepository.save(CapacidadTecnologiaMapper.toEntity(capacidadTecnologia))
				.map(CapacidadTecnologiaMapper::toDomain);
	}

	

}
