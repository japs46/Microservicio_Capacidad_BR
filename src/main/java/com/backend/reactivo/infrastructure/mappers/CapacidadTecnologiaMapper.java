package com.backend.reactivo.infrastructure.mappers;

import com.backend.reactivo.domain.models.CapacidadTecnologia;
import com.backend.reactivo.infrastructure.entities.CapacidadTecnologiaEntity;

import reactor.core.publisher.Mono;

public class CapacidadTecnologiaMapper {

	public static CapacidadTecnologia toDomain(CapacidadTecnologiaEntity entity) {
        return new CapacidadTecnologia(entity.getId(), entity.getCapacidad_id(), entity.getTecnologia_id());
    }

    public static CapacidadTecnologiaEntity toEntity(CapacidadTecnologia capacidadTecnologia) {
        return new CapacidadTecnologiaEntity(capacidadTecnologia.getId(), capacidadTecnologia.getCapacidad_id(), capacidadTecnologia.getTecnologia_id());
    }
    
    public static Mono<CapacidadTecnologia> toMonoDomain(Mono<CapacidadTecnologiaEntity> monoCapacidadTecnologiaEntity) {
		return monoCapacidadTecnologiaEntity.map(CapacidadTecnologiaMapper::toDomain);
	}
    
    public static Mono<CapacidadTecnologiaEntity> toMonoEntity(Mono<CapacidadTecnologia> monoCapacidadTecnologia) {
		return monoCapacidadTecnologia.map(CapacidadTecnologiaMapper::toEntity);
	}
	
}
