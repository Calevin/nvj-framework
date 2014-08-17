package org.calevin.navaja.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class AdministradorRecursosMock implements AdministradorRecursosNvjItf {

    private String uriPropertiesBDD = null;
	
	@Override
	public void iniciarRecursos() throws IOException {
	}

	@Override
	public DataSource proveerDataSource() {
        DataSource dataSource = null;
        Properties propertiesBDD = new Properties();
        try {
            propertiesBDD.load(new FileInputStream(uriPropertiesBDD));

            dataSource = BasicDataSourceFactory.createDataSource(propertiesBDD);
        } catch (IOException ex) {
            System.out.println("Error al cargar archivo: " + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex);
        }
            return dataSource;
	}

	public String getUriPropertiesBDD() {
		return uriPropertiesBDD;
	}

	public void setUriPropertiesBDD(String uriPropertiesBDD) {
		this.uriPropertiesBDD = uriPropertiesBDD;
	}

}
