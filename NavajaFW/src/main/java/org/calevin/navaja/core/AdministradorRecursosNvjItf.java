package org.calevin.navaja.core;

import java.io.IOException;

import javax.sql.DataSource;

/**
 * Interface que representa el administrador de recursos.
 * @author calevin
 */
public interface AdministradorRecursosNvjItf {

    /**
     * Inicializa los recursos para la app 
     */
    public void iniciarRecursos() throws IOException;

    /**
     * Provee un DataSource para conectase a la Base de Datos
     * @return DataSource ha la Base de Datos
     */
    public DataSource proveerDataSource();
}
