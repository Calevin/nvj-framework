package org.calevin.navaja.excepciones.core;

import java.sql.SQLException;

import org.calevin.navaja.util.NavajaConstantes;

public class CargadorRecursosConexionFallidaException extends
		CargadorRecursosException {

	private static final long serialVersionUID = 3639964735307634346L;
	
	private static final String CONEXION_FALLIDA = "Conexion fallida";

	public CargadorRecursosConexionFallidaException(String mensaje) {
        super(CONEXION_FALLIDA
        		+ NavajaConstantes.PUNTO_ESPACIO
        		+ mensaje);
	}
	
	public CargadorRecursosConexionFallidaException(Exception e) {
        super(CONEXION_FALLIDA
        		+ NavajaConstantes.PUNTO_ESPACIO
        		+ e);
	}
	
	public CargadorRecursosConexionFallidaException(SQLException e) {
        super(CONEXION_FALLIDA
        		+ NavajaConstantes.COMA_ESPACIO
        		+ NavajaConstantes.ERROR
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.DE
        		+ NavajaConstantes.ESPACIO
        		+ NavajaConstantes.TIPO_SQL 
        		+ NavajaConstantes.PUNTO_ESPACIO
        		+ e);
	}
}
