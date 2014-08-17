package org.calevin.navaja.excepciones.core;

import org.calevin.navaja.util.NavajaConstantes;

public class CargadorRecursosDataSourceNuloException extends
		CargadorRecursosException {

	private static final long serialVersionUID = -4492703588025503748L;

    private static final String DATA_SOURCE_NULO = "DataSource provisto nulo";
    
    public CargadorRecursosDataSourceNuloException() {
        super(DATA_SOURCE_NULO + NavajaConstantes.PUNTO);
    }    
}
