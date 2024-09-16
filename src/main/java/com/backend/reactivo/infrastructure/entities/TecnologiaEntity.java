package com.backend.reactivo.infrastructure.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tecnologia")
public class TecnologiaEntity {
	
	@Id
	private final Long id;
	
	private final String nombre;
	
	private final String descripcion;
	
	public TecnologiaEntity(Long id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public String toString() {
		return "TecnologiaEntity{" +
	               "id=" + id +
	               ", nombre='" + nombre + '\'' +
	               ", descripcion='" + descripcion + '\'' +
	               '}';
	}
}
