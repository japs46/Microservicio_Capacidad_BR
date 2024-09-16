package com.backend.reactivo.infrastructure.mappers;

import com.backend.reactivo.domain.models.Tecnologia;
import com.backend.reactivo.infrastructure.entities.TecnologiaEntity;

public class TecnologiaMapper {

	public static Tecnologia toDomain(TecnologiaEntity entity) {
        return new Tecnologia(entity.getId(), entity.getNombre(), entity.getDescripcion());
    }

    public static TecnologiaEntity toEntity(Tecnologia tecnologia) {
        return new TecnologiaEntity(tecnologia.getId(), tecnologia.getNombre(), tecnologia.getDescripcion());
    }
	
}
