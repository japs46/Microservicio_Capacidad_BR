package com.backend.reactivo.infrastructure.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.reactivo.infrastructure.entities.TecnologiaEntity;

import reactor.core.publisher.Flux;

@Repository
public interface TecnologiaEntityRepository extends ReactiveCrudRepository<TecnologiaEntity, Long> {

	@Query("SELECT t.id, t.nombre, t.descripcion " + "FROM tecnologia t "
			+ "INNER JOIN capacidad_tecnologia ct ON t.id = ct.tecnologia_id " + "WHERE ct.capacidad_id = :capacidadId")
	Flux<TecnologiaEntity> findTecnologiasByCapacidadId(Long capacidadId);
}
