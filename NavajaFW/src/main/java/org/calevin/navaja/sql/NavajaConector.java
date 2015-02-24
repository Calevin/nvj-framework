package org.calevin.navaja.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.calevin.navaja.excepciones.sql.CerrarRecursoException;
import org.calevin.navaja.mapeo.RaizMapeo;

/**
 * La clase principal del paquete sql, se utiliza para conectarse a la BDD
 *
 * @author calevin
 */
public class NavajaConector {
	
	//INSTANCE es la unica instancia del Singleton
    private static NavajaConector INSTANCE = null;
    
    /*
     * mapeoRaiz Es el mapeo total que utilizara la app, creado a partir de los
     * archivos de configuracion
     */
    private RaizMapeo raizMapeo = null;
    
    //dataSource DataSource para conectarse a la BDD
    private DataSource dataSource = null;

    //Constructor protegido porque es un Singleton
    protected NavajaConector() {
    }

	/**
	 * Se ejecuta la query recibida por parametro 
     * @param query
     * 			consulta a ejecutar
	 * @return retora el numero de rows afectadas por la query, 0 en caso de no afectar registros
     * @throws SQLException
     * @throws CerrarRecursoException 
	 */
    public static int ejecutarUpdate(String query) throws SQLException, CerrarRecursoException {
    	int rta = -1;
        Connection con = null;
        PreparedStatement pstm = null;

        con = NavajaConector.getInstance().getDataSource().getConnection();
        pstm = con.prepareStatement(query);

        rta = pstm.executeUpdate();

		cerrarRecurso(con);
		cerrarRecurso(pstm);

    	return rta;
    }
    
    /**
     * Se ejecuta la query recibida por parametro 
     * @param query
     * 			consulta a ejecutar
     * @return un ResultSet que contiene el resultado de la query; nunca null
     * @throws SQLException
     * @throws CerrarRecursoException
     */
    public static ResultSet ejecutarQuery(String query) throws SQLException, CerrarRecursoException {
    	ResultSet rta = null;
        Connection con = null;
        PreparedStatement pstm = null;

        con = NavajaConector.getInstance().getDataSource().getConnection();
        pstm = con.prepareStatement(query);

        rta = pstm.executeQuery();

		cerrarRecurso(con);
		cerrarRecurso(pstm);

    	return rta;
    }    
    
    /**
     * Cierra el recurso pasado por parametro.
     * @param recurso 
     * 			recurso a cerrar.
     * @throws CerrarRecursoException
     */
	public static void cerrarRecurso(AutoCloseable recurso) throws CerrarRecursoException{
	        if (null != recurso) {
	        	try {
	        	recurso.close();
	        	} catch (Exception e){
	        		throw new CerrarRecursoException(recurso, e);
	        	}
	    }		
	}

    /**
     * Provee la instancia del Singleton
     *
     * @return la instancia del Singleton
     */
    public static NavajaConector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NavajaConector();
        }
        return INSTANCE;
    }
    
    public RaizMapeo getRaizMapeo() {
        return raizMapeo;
    }

    public void setMapeoRaiz(RaizMapeo raizMapeo) {
        this.raizMapeo = raizMapeo;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
