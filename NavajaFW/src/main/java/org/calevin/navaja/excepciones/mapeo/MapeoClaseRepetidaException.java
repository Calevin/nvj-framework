package org.calevin.navaja.excepciones.mapeo;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class MapeoClaseRepetidaException extends MapeoException {

	private static final long serialVersionUID = -9094317017672725480L;

	public MapeoClaseRepetidaException(String nombreClase) {
        super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.CLASE, 0)
                + NavajaConstantes.ESPACIO
                + nombreClase
                + NavajaConstantes.ESPACIO
                + NavajaConstantes.DEFINIDA_DOS_VECES
                + NavajaConstantes.PUNTO);
    }
}
