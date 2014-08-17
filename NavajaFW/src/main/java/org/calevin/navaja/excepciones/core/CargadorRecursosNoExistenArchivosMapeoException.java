package org.calevin.navaja.excepciones.core;

import org.calevin.navaja.util.NavajaConstantes;

public class CargadorRecursosNoExistenArchivosMapeoException extends
		CargadorRecursosException {

	private static final long serialVersionUID = 3886017334420500119L;

    private static final String ARCHIVOS_DE_MAPEO_INEXISTENTES = "Archivos de mapeos inexistentes";
	
	public CargadorRecursosNoExistenArchivosMapeoException() {
        super(ARCHIVOS_DE_MAPEO_INEXISTENTES 
                + NavajaConstantes.EN 
                + NavajaConstantes.ESPACIO
                + NavajaConstantes.CARPETA_DE_CONF
                + NavajaConstantes.PUNTO);
	}
}
