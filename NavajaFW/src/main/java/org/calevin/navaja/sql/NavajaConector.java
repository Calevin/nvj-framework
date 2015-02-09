package org.calevin.navaja.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

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

    //TODO crear metodo para realizar querys
    
    /**
     * 
     */
    public static int ejecutarUpdate(String query){
    	int rta = -1;
        Connection con = null;
        PreparedStatement pstm = null;

        try {
        con = NavajaConector.getInstance().getDataSource().getConnection();
        pstm = con.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
        System.out.println("Por ejecutar " + pstm.toString());	
        rta = pstm.executeUpdate();
        System.out.println("Cambios DML por la ejecucion: " + rta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			cerrarRecurso(con);
			cerrarRecurso(pstm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return rta;
    }
    
    public static ResultSet ejecutarQuery(String query){
    	ResultSet rta = null;
        Connection con = null;
        PreparedStatement pstm = null;

        try {
        con = NavajaConector.getInstance().getDataSource().getConnection();
        pstm = con.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
        System.out.println("Por ejecutar " + pstm.toString());	
        rta = pstm.executeQuery();
        System.out.println("Registros obtenidos: " + rta.getFetchSize());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			cerrarRecurso(con);
			cerrarRecurso(pstm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return rta;
    }    
    
    //ESTE METODO TAMBIEN ESTA EN NavajaDAO, PARA CERRAR RECURSOS
    private static void cerrarRecurso(Connection conn) throws SQLException {
        if (null != conn) {
                conn.close();
        }
    }

    private static void cerrarRecurso(Statement stm) throws SQLException {
        if (null != stm) {
                stm.close();
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
