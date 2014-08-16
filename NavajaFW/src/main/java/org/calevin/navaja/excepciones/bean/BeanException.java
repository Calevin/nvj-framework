package org.calevin.navaja.excepciones.bean;

import org.calevin.navaja.util.NavajaConstantes;

public class BeanException extends Exception {

	private static final long serialVersionUID = 5138430799847582544L;

	private static final String EXCEPCION_AL_USANDO_BEAN = "Excepcion usando un bean";

	public BeanException() {
		super(EXCEPCION_AL_USANDO_BEAN + NavajaConstantes.PUNTO);
	}

	public BeanException(String mensaje) {
		super(EXCEPCION_AL_USANDO_BEAN 
				+ NavajaConstantes.PUNTO
                + NavajaConstantes.ESPACIO 
                + mensaje);
	}
	
}
