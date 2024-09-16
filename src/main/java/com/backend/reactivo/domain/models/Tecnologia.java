package com.backend.reactivo.domain.models;

public class Tecnologia {

	private final Long id;
	
	private final String nombre;
	
	private final String descripcion;
	
	public Tecnologia(Long id, String nombre, String descripcion) {
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
		return "Tecnologia{" +
	               "id=" + id +
	               ", nombre='" + nombre + '\'' +
	               ", descripcion='" + descripcion + '\'' +
	               '}';
	}
	
}
