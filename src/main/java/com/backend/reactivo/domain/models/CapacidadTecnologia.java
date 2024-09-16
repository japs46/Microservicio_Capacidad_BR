package com.backend.reactivo.domain.models;

public class CapacidadTecnologia {

	private Long id;
	
	private Long capacidad_id;
	
	private Long tecnologia_id;

	public CapacidadTecnologia(Long id, Long capacidad_id, Long tecnologia_id) {
		this.id = id;
		this.capacidad_id = capacidad_id;
		this.tecnologia_id = tecnologia_id;
	}

	public Long getId() {
		return id;
	}

	public Long getCapacidad_id() {
		return capacidad_id;
	}

	public Long getTecnologia_id() {
		return tecnologia_id;
	}
}
