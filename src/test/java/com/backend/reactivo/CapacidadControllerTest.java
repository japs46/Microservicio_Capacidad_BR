package com.backend.reactivo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.backend.reactivo.application.dto.ResponseCapacidad;
import com.backend.reactivo.application.services.CapacidadService;
import com.backend.reactivo.domain.models.Capacidad;
import com.backend.reactivo.domain.models.Tecnologia;
import com.backend.reactivo.infrastructure.controllers.CapacidadController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(CapacidadController.class)
public class CapacidadControllerTest {

	@Autowired 
	private WebTestClient webTestClient;
	
	@MockBean
	private CapacidadService capacidadService;
	
	@Test
	public void crearCapacidadOkTest() {
		List<Tecnologia> listaTecnologia = List.of(new Tecnologia(1L, "Java", "descripcion java"),
				new Tecnologia(2L,"Python","descripcion python"),
				new Tecnologia(3L,"JavaScript","descripcion JavaScript"),
				new Tecnologia(4L,"Postman","desxripcion postman"),
				new Tecnologia(5L,"SQL","descripcion sql"),
				new Tecnologia(6L,"NoSQL","descripcion nosql"));
		
		Capacidad capacidad = new Capacidad(90L, "Capacidad1", "descripcion capacidad1", listaTecnologia);
		Mono<ResponseCapacidad> monoCapacidad= Mono.just(new ResponseCapacidad("Ok", capacidad, true));
		when(capacidadService.createCapacidad(any())).thenReturn(monoCapacidad);
		
		webTestClient.post().uri("/api/capacidades/guardar").bodyValue(capacidad)
		.exchange()
		.expectStatus().isCreated();
		 verify(capacidadService,times(1)).createCapacidad(any());
	}
	
	@Test
	public void crearCapacidadSinTecnologiaTest() {
		Capacidad capacidad = new Capacidad(90L, "Capacidad1", "descripcion capacidad1", null);
		Mono<ResponseCapacidad> monoCapacidad= Mono.just(new ResponseCapacidad("debe asociar tecnologias", capacidad, false));
		when(capacidadService.createCapacidad(any())).thenReturn(monoCapacidad);
		
		webTestClient.post().uri("/api/capacidades/guardar").bodyValue(capacidad)
		.exchange()
		.expectStatus().isAccepted();
		
		 verify(capacidadService,times(1)).createCapacidad(any());
	}
	
	@Test
	public void obtenerListadoTecnologiasPaginadas() {
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
		
		when(capacidadService.getAllCapacidadesPag(0, 2, "nombre","asc")).thenReturn(test1);
		when(capacidadService.getAllCapacidadesPag(0, 3, "cantidadTecnologias","asc")).thenReturn(test4);
		
		webTestClient.get().uri("/api/capacidades/obtenerPag?page=0&size=2&sortBy=nombre&direction=asc")
	    .exchange()
	    .expectStatus().isOk()
	    .expectBodyList(Capacidad.class)
	    .consumeWith(response -> {
	        List<Capacidad> capacidades = response.getResponseBody();
	        assertNotNull(capacidades);
	        assertFalse(capacidades.isEmpty());
	        assertEquals(2,capacidades.size());
	        assertEquals("Capacidad1", capacidades.get(0).getNombre());
	        assertEquals(1L, capacidades.get(0).getId());
	        assertEquals(2,capacidades.get(0).getListaTecnologia().size());
	    });
		
		webTestClient.get().uri("/api/capacidades/obtenerPag?page=0&size=3&sortBy=cantidadTecnologias&direction=asc")
	    .exchange()
	    .expectStatus().isOk()
	    .expectBodyList(Capacidad.class)
	    .consumeWith(response -> {
	        List<Capacidad> capacidades = response.getResponseBody();
	        assertNotNull(capacidades);
	        assertFalse(capacidades.isEmpty());
	        assertEquals(3,capacidades.size());
	        assertEquals("Capacidad3", capacidades.get(1).getNombre());
	        assertEquals(3L, capacidades.get(1).getId());
	        assertEquals(2,capacidades.get(0).getListaTecnologia().size());
	    });
		
		verify(capacidadService,times(1)).getAllCapacidadesPag(0, 2, "nombre","asc");
		verify(capacidadService,times(1)).getAllCapacidadesPag(0, 3, "cantidadTecnologias","asc");
	}
}
