package org.calevin.navaja.excepciones.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.calevin.navaja.util.NavajaConstantes;

public class CerrarRecursoException extends Exception{

	private static final long serialVersionUID = -8864789571016713234L;

    private static final String ERROR_AL_CERRAR = "Error al cerrar";
    private static final String RECURSO = "recurso";
    private static final String CONNECTION = "conexion";
    private static final String STATEMENT = "statement";
    private static final String RESULTSET = "resultSet" ;   
    
    public CerrarRecursoException(){
    	super (ERROR_AL_CERRAR + NavajaConstantes.ESPACIO + RECURSO);
    }

    public CerrarRecursoException(AutoCloseable recurso, Exception e){
    	super (ERROR_AL_CERRAR + NavajaConstantes.ESPACIO + RECURSO);
    }
    
    public CerrarRecursoException(Connection recurso, Exception e){
    	super (ERROR_AL_CERRAR 
    			+ NavajaConstantes.ESPACIO 
    			+ NavajaConstantes.LA
    			+ NavajaConstantes.ESPACIO 
    			+ CONNECTION
    			+ NavajaConstantes.ESPACIO 
    			+ recurso
    			, e ); 
    }
    
    public CerrarRecursoException(Statement recurso, Exception e){
    	super (ERROR_AL_CERRAR 
    			+ NavajaConstantes.ESPACIO 
    			+ NavajaConstantes.LA
    			+ NavajaConstantes.ESPACIO 
    			+ STATEMENT
    			+ NavajaConstantes.ESPACIO 
    			+ recurso
    			, e ); 
    }
    
    public CerrarRecursoException(ResultSet recurso, Exception e){
    	super (ERROR_AL_CERRAR 
    			+ NavajaConstantes.ESPACIO 
    			+ NavajaConstantes.LA
    			+ NavajaConstantes.ESPACIO 
    			+ RESULTSET
    			+ NavajaConstantes.ESPACIO 
    			+ recurso
    			, e ); 
    }
}
