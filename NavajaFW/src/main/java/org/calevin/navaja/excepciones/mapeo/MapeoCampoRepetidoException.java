package org.calevin.navaja.excepciones.mapeo;

import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class MapeoCampoRepetidoException extends MapeoExcepcion {

	private static final long serialVersionUID = 4011846282251208538L;

	public MapeoCampoRepetidoException(String campo) {

        super(NavajaStringUtil.conmutarCaseChar(NavajaConstantes.CAMPO, 0)
                + NavajaConstantes.ESPACIO_COMILLA
                + campo
                + NavajaConstantes.COMILLA_ESPACIO 
                + NavajaConstantes.DEFINIDO_DOS_VECES
                + NavajaConstantes.PUNTO);
    }
}
