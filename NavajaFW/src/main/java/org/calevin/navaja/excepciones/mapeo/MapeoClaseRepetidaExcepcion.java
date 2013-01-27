package org.calevin.navaja.excepciones.mapeo;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class MapeoClaseRepetidaExcepcion extends MapeoExcepcion {

	private static final long serialVersionUID = -9094317017672725480L;

	public MapeoClaseRepetidaExcepcion(String nombreClase) {
        super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.CLASE, 0)
                + NavajaConstantes.ESPACIO
                + nombreClase
                + NavajaConstantes.ESPACIO
                + NavajaConstantes.DEFINIDA_DOS_VECES
                + NavajaConstantes.PUNTO);
    }
}
