package org.calevin.navaja.sql;

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
