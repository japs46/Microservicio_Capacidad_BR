package com.backend.reactivo.infrastructure.mappers;

import com.backend.reactivo.domain.models.Capacidad;
import com.backend.reactivo.infrastructure.entities.CapacidadEntity;

import reactor.core.publisher.Mono;

public class CapacidadMapper {

	public static Capacidad toDomain(CapacidadEntity entity) {
        return new Capacidad(entity.getId(), entity.getNombre(), entity.getDescripcion(),null);
    }

    public static CapacidadEntity toEntity(Capacidad capacidad) {
        return new CapacidadEntity(capacidad.getId(), capacidad.getNombre(), capacidad.getDescripcion());
    }
    
    public static Mono<Capacidad> toMonoDomain(Mono<CapacidadEntity> monoCapacidadEntity) {
		return monoCapacidadEntity.map(CapacidadMapper::toDomain);
	}
    
    public static Mono<CapacidadEntity> toMonoEntity(Mono<Capacidad> monoCapacidad) {
		return monoCapacidad.map(CapacidadMapper::toEntity);
	}
	
}
