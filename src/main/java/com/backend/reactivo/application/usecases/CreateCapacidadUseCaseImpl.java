package com.backend.reactivo.application.usecases;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.reactivo.application.dto.ResponseCapacidad;
import com.backend.reactivo.domain.models.Capacidad;
import com.backend.reactivo.domain.models.CapacidadTecnologia;
import com.backend.reactivo.domain.models.Tecnologia;
import com.backend.reactivo.domain.ports.in.CreateCapacidadUseCase;
import com.backend.reactivo.domain.ports.out.CapacidadRepositoryPort;
import com.backend.reactivo.domain.ports.out.CapacidadTecnologiaRepositoryPort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CreateCapacidadUseCaseImpl implements CreateCapacidadUseCase{
	
	private final CapacidadRepositoryPort capacidadRepositoryPort;
	private final CapacidadTecnologiaRepositoryPort capacidadTecnologiaRepositoryPort;
	
	public CreateCapacidadUseCaseImpl(CapacidadRepositoryPort capacidadRepositoryPort,CapacidadTecnologiaRepositoryPort capacidadTecnologiaRepositoryPort) {
		this.capacidadRepositoryPort = capacidadRepositoryPort;
		this.capacidadTecnologiaRepositoryPort = capacidadTecnologiaRepositoryPort;
	}

	@Override
	public Mono<ResponseCapacidad> createCapacidad(Capacidad capacidad) {
		// Validación: Debe haber tecnologías asociadas
	    if (capacidad.getListaTecnologia() == null || capacidad.getListaTecnologia().isEmpty()) {
	        return Mono.just(new ResponseCapacidad("Debe asociar tecnologías a la capacidad", capacidad, false));
	    }

	    // Validación: Mínimo 3 tecnologías
	    if (capacidad.getListaTecnologia().size() < 3) {
	        return Mono.just(new ResponseCapacidad("La capacidad debe tener como mínimo 3 tecnologías asociadas", capacidad, false));
	    }

	    // Validación: Máximo 20 tecnologías
	    if (capacidad.getListaTecnologia().size() > 20) {
	        return Mono.just(new ResponseCapacidad("La capacidad debe tener como máximo 20 tecnologías asociadas", capacidad, false));
	    }

	    // Validación: Tecnologías duplicadas (IDs únicos)
	    Set<Long> uniqueTecnologiaIds = capacidad.getListaTecnologia().stream()
	            .map(Tecnologia::getId) // Obtener los IDs de las tecnologías
	            .collect(Collectors.toSet());

	    if (uniqueTecnologiaIds.size() != capacidad.getListaTecnologia().size()) {
	        return Mono.just(new ResponseCapacidad("La lista de tecnologías contiene duplicados", capacidad, false));
	    }

	    // Si todas las validaciones pasan, guardamos la entidad Capacidad
	    return capacidadRepositoryPort.save(capacidad)
	        .flatMap(savedCapacidad -> 
	            // Guardar manualmente cada relación Capacidad-Tecnologia
	            Flux.fromIterable(capacidad.getListaTecnologia())
	                .flatMap(tecnologia -> {
	                    // Crear y guardar la entidad intermedia para la relación Capacidad-Tecnologia
	                    CapacidadTecnologia capacidadTecnologia = 
	                        new CapacidadTecnologia(null, savedCapacidad.getId(), tecnologia.getId());

	                    return capacidadTecnologiaRepositoryPort.save(capacidadTecnologia);
	                })
	                // Luego de guardar todas las relaciones, devolvemos la capacidad guardada
	                .then(Mono.just(new ResponseCapacidad("Se registró la capacidad correctamente", savedCapacidad, true)))
	        );
	}

}
