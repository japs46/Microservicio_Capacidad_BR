package com.backend.reactivo.application.usecases;

import org.springframework.stereotype.Component;

import com.backend.reactivo.domain.models.Capacidad;
import com.backend.reactivo.domain.ports.in.RetrieveCapacidadUseCase;
import com.backend.reactivo.domain.ports.out.CapacidadRepositoryPort;
import com.backend.reactivo.domain.ports.out.TecnologiaRepositoryPort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RetrieveCapacidadUseCaseImpl implements RetrieveCapacidadUseCase {

	private final CapacidadRepositoryPort capacidadRepositoryPort;
	private final TecnologiaRepositoryPort tecnologiaRepositoryPort;

	public RetrieveCapacidadUseCaseImpl(CapacidadRepositoryPort capacidadRepositoryPort,
			TecnologiaRepositoryPort tecnologiaRepositoryPort) {
		this.capacidadRepositoryPort = capacidadRepositoryPort;
		this.tecnologiaRepositoryPort = tecnologiaRepositoryPort;
	}

	@Override
	public Mono<Capacidad> getCapacidad(Long id) {
		return capacidadRepositoryPort.findById(id);
	}

	@Override
	public Mono<Capacidad> getCapacidadByNombre(String nombre) {
		return capacidadRepositoryPort.findByNombre(nombre);
	}

	@Override
	public Flux<Capacidad> getAllCapacidades() {
		return capacidadRepositoryPort.findAll();
	}

	@Override
	public Flux<Capacidad> getAllCapacidadesPag(int page, int size, String sortBy, String direction) {
		// Obtener todas las capacidades
		return capacidadRepositoryPort.findAll().flatMap(capacidad ->
		// Para cada capacidad, obtener las tecnologías asociadas
		tecnologiaRepositoryPort.getTecnologiasByCapacidad(capacidad.getId()).collectList()
				.map(tecnologias -> new Capacidad(capacidad.getId(), capacidad.getNombre(), capacidad.getDescripcion(),
						tecnologias)))
				.sort((c1, c2) -> {
					if ("desc".equalsIgnoreCase(direction)) {
						if ("nombre".equalsIgnoreCase(sortBy)) {
							return c2.getNombre().compareTo(c1.getNombre());
						} else if ("cantidadTecnologias".equalsIgnoreCase(sortBy)) {
							return Integer.compare(c2.getListaTecnologia().size(), c1.getListaTecnologia().size());
						}
					} else {
						if ("nombre".equalsIgnoreCase(sortBy)) {
							return c1.getNombre().compareTo(c2.getNombre());
						} else if ("cantidadTecnologias".equalsIgnoreCase(sortBy)) {
							return Integer.compare(c1.getListaTecnologia().size(), c2.getListaTecnologia().size());
						}
					}
					return 0; // Default case if sortBy is not recognized
				}).skip((long) page * size).take(size);
	}

}
