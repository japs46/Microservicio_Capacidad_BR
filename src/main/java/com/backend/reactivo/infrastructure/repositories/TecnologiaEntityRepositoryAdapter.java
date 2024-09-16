package com.backend.reactivo.infrastructure.repositories;

import org.springframework.stereotype.Component;

import com.backend.reactivo.domain.models.Tecnologia;
import com.backend.reactivo.domain.ports.out.TecnologiaRepositoryPort;
import com.backend.reactivo.infrastructure.mappers.TecnologiaMapper;

import reactor.core.publisher.Flux;

@Component
public class TecnologiaEntityRepositoryAdapter implements TecnologiaRepositoryPort{
	
	private final TecnologiaEntityRepository tecnologiaEntityRepository;
	
	public TecnologiaEntityRepositoryAdapter(TecnologiaEntityRepository tecnologiaEntityRepository) {
		this.tecnologiaEntityRepository = tecnologiaEntityRepository;
	}

	@Override
	public Flux<Tecnologia> getTecnologiasByCapacidad(Long capacidadId) {
		return tecnologiaEntityRepository.findTecnologiasByCapacidadId(capacidadId)
				.map(TecnologiaMapper::toDomain);
	}

}
