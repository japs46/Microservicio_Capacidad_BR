package com.backend.reactivo.application.dto;

import com.backend.reactivo.domain.models.Capacidad;

public class ResponseCapacidad {

	private String mensaje;
	
	private Capacidad capacidad;
	
	private boolean status;

	public ResponseCapacidad(String mensaje, Capacidad tecnologia, boolean status) {
		this.mensaje = mensaje;
		this.capacidad = tecnologia;
		this.status = status;
	}

	public String getMensaje() {
		return mensaje;
	}

	public Capacidad getCapacidad() {
		return capacidad;
	}

	public boolean isStatus() {
		return status;
	}
	
}
