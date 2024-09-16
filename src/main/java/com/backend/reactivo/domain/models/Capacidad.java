package com.backend.reactivo.domain.models;

import java.util.List;

public class Capacidad {
	
	private final Long id;
	
	private final String nombre;
	
	private final String descripcion;
	
	private final List<Tecnologia> listaTecnologia;

	public Capacidad(Long id, String nombre, String descripcion, List<Tecnologia> listaTecnologia) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.listaTecnologia = listaTecnologia;
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
	
	public List<Tecnologia> getListaTecnologia() {
		return listaTecnologia;
	}

	@Override
    public String toString() {
        return "Capacidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
	
}
