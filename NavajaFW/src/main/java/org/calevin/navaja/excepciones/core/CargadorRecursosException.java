package org.calevin.navaja.excepciones.core;

import org.calevin.navaja.util.NavajaConstantes;

public class CargadorRecursosException extends Exception {

	private static final long serialVersionUID = 7858593468318492164L;

	private static final String EXCEPCION_AL_CARGAR_RECURSOS = "Excepcion al cargar recursos";

	public CargadorRecursosException() {
		super(EXCEPCION_AL_CARGAR_RECURSOS + NavajaConstantes.PUNTO);
	}	
	
	public CargadorRecursosException(String mensaje) {
		super(EXCEPCION_AL_CARGAR_RECURSOS
				+ NavajaConstantes.PUNTO
                + NavajaConstantes.ESPACIO 
                + mensaje);
	}
}
