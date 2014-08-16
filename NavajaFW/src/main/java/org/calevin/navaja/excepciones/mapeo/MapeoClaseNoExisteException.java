package org.calevin.navaja.excepciones.mapeo;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class MapeoClaseNoExisteException extends MapeoException {

	private static final long serialVersionUID = -2186669854652969686L;

	public MapeoClaseNoExisteException(String nombreClase) {
        super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.CLASE, 0) 
                + NavajaConstantes.ESPACIO_COMILLA
                + nombreClase
                + NavajaConstantes.COMILLA_ESPACIO
                + NavajaConstantes.NO_EXISTE
                + NavajaConstantes.ESPACIO
                + NavajaConstantes.EN_EL_MAPEO
                + NavajaConstantes.PUNTO);
        
    }
}
