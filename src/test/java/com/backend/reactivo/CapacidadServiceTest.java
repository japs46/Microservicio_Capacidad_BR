package com.backend.reactivo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.backend.reactivo.application.dto.ResponseCapacidad;
import com.backend.reactivo.application.services.CapacidadService;
import com.backend.reactivo.domain.models.Capacidad;
import com.backend.reactivo.domain.models.Tecnologia;
import com.backend.reactivo.domain.ports.in.CreateCapacidadUseCase;
import com.backend.reactivo.domain.ports.in.RetrieveCapacidadUseCase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
public class CapacidadServiceTest {

	@Mock
	private CreateCapacidadUseCase createCapacidadUseCase;

	@Mock
	private RetrieveCapacidadUseCase retrieveCapacidadUseCase;

	@InjectMocks
	private CapacidadService capacidadService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Inicializa los mocks
	}
	
	@Test
	public void createCapacidadTest() {
		List<Tecnologia> tecnologias = List.of(new Tecnologia(1L, "Java", "descripcion java"),
				new Tecnologia(2L,"Python","descripcion python"),
				new Tecnologia(3L,"JavaScript","descripcion JavaScript"));
		
		Capacidad capacidad = new Capacidad(1L, "Capacidad1", "descripcion",tecnologias);
		Mono<ResponseCapacidad> monoResponse= Mono.just(new ResponseCapacidad("Se registro correctamente", capacidad, true));
		when(createCapacidadUseCase.createCapacidad(capacidad)).thenReturn(monoResponse);

		StepVerifier.create(capacidadService.createCapacidad(capacidad)).expectSubscription()
		.assertNext(response-> {
			assertNotNull(response);
			assertEquals("Se registro correctamente",response.getMensaje());
			assertEquals(true, response.isStatus());
			assertEquals(1L, response.getCapacidad().getId());
			assertEquals(3, response.getCapacidad().getListaTecnologia().size());
		}).expectComplete();
		
		verify(createCapacidadUseCase).createCapacidad(capacidad);

	}
	
	@Test
	public void obtenerCapacidadesPaginadasTest() {
		Flux<Capacidad> fluxCapacidad= Flux.just(new Capacidad(1L, "Capacidad1", "descripcion capacidad1",
				List.of(new Tecnologia(1L, "Java", "descripcion java"),
						new Tecnologia(2L,"Python","descripcion python"))),
				new Capacidad(2L,"Capacidad2","descripcion python",null),
				new Capacidad(3L,"Capacidad3","descripcion JavaScript",
						List.of(new Tecnologia(5L,"SQL","descripcion sql"),
								new Tecnologia(6L,"NoSQL","descripcion nosql"))),
				new Capacidad(4L,"Capacidad4","desxripcion postman",null),
				new Capacidad(5L,"Capacidad5","descripcion sql",
						List.of(new Tecnologia(3L,"JavaScript","descripcion JavaScript"),
								new Tecnologia(4L,"Postman","desxripcion postman"))),
				new Capacidad(6L,"Capacidad6","descripcion nosql",null));
		
		Flux<Capacidad> test1 = fluxCapacidad.filter(capacidad -> capacidad.getId() == 1 || capacidad.getId() == 2);
		Flux<Capacidad> test4 = fluxCapacidad.filter(capacidad -> capacidad.getId() == 1 || capacidad.getId() == 3 || capacidad.getId()==6);
		
		when(retrieveCapacidadUseCase.getAllCapacidadesPag(0, 2, "nombre","asc")).thenReturn(test1);
		when(retrieveCapacidadUseCase.getAllCapacidadesPag(0, 3, "cantidadTecnologias","asc")).thenReturn(test4);
		
		StepVerifier.create(capacidadService.getAllCapacidadesPag(0, 2, "nombre","asc").log())
		.expectSubscription()
		.expectNextMatches(capacity -> capacity.getNombre().equals("Capacidad1"))
		.expectNextMatches(capacity -> capacity.getNombre().equals("Capacidad2"))
		.expectComplete();
		
		StepVerifier.create(capacidadService.getAllCapacidadesPag(0, 3, "cantidadTecnologias","asc").log())
		.expectSubscription()
		.assertNext(capacity -> {
			assertNotNull(capacity);
			assertEquals("Capacidad1",capacity.getNombre());
			assertEquals(1L, capacity.getId());
			assertEquals(2, capacity.getListaTecnologia().size());
		})
		.assertNext(capacity -> {
			assertNotNull(capacity);
			assertEquals("Capacidad2",capacity.getNombre());
			assertEquals(3L, capacity.getId());
			assertEquals(2, capacity.getListaTecnologia().size());
		})
		.assertNext(capacity -> {
			assertNotNull(capacity);
			assertEquals("Capacidad3",capacity.getNombre());
			assertEquals(6L, capacity.getId());
			assertEquals(2, capacity.getListaTecnologia().size());
		})
		.expectComplete();
		
		verify(retrieveCapacidadUseCase,times(1)).getAllCapacidadesPag(0, 2, "nombre","asc");
		verify(retrieveCapacidadUseCase,times(1)).getAllCapacidadesPag(0, 3, "cantidadTecnologias","asc");

	}
}
