package org.calevin.navaja.core;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.calevin.navaja.mapeo.CampoMapeo;
import org.calevin.navaja.mapeo.Mapeador;
import org.calevin.navaja.mapeo.PrimaryKeyMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;
import org.calevin.navaja.sql.NavajaConector;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class CargadorRecursosNvjTest {

    private String nombreClasePrimera = "org.empresa.beans.PruebaCargador";    
	private String uriPropertiesBDD = null;	
	private AdministradorRecursosMock recursosProyecto = null;
    private ArrayList<String> nombresClase = new ArrayList<String>();	
    private ArrayList<TablaMapeo> tablas = new ArrayList<TablaMapeo>();
    private TablaMapeo tablaPrimera = null;
    private PrimaryKeyMapeo pk = null;
    
	@Before
	public void setUp() {

		//Tablas
		tablaPrimera = new TablaMapeo();
		tablaPrimera.setNombre("pruebaCargador");
		tablaPrimera.setNombreComoClase(nombreClasePrimera);
		tablaPrimera.getCampos().add(0, new CampoMapeo("id","idCargador"));
		tablaPrimera.getCampos().add(1, new CampoMapeo("des","desCargador"));
		pk = new PrimaryKeyMapeo();
		pk.getCampos().add(new CampoMapeo("idCargador",null));
		tablaPrimera.setPrimaryKeyMapeo(pk);
		
		uriPropertiesBDD = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebacargador/prueba_cargador_recursos.properties";
		recursosProyecto = new AdministradorRecursosMock();
		recursosProyecto.setUriPropertiesBDD(uriPropertiesBDD);
	}

	@After 
	public void tearDown(){
		Mapeador.limpiarMapeo();
	}
	
	@AfterClass
	static public void tearDownClass(){
        NavajaConector.getInstance().setMapeoRaiz(null);
        NavajaConector.getInstance().setDataSource(null);
	}
	
	@Test
	public void cargarRecursosCasoCorrectoTest(){
		try {
			CargadorRecursosNvj.cargarRecursos(ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebacargador/", recursosProyecto);
			String urlNavajaConector = ((BasicDataSource) NavajaConector.getInstance().getDataSource()).getUrl();
			String urlDatasource = ((BasicDataSource) recursosProyecto.proveerDataSource()).getUrl();
			Assert.assertTrue(urlNavajaConector.equals(urlDatasource));
			
			//Probando nombreClases
			nombresClase = NavajaConector.getInstance().getRaizMapeo().getNombresClase();
			Assert.assertTrue(nombresClase.size() == 1);
			Assert.assertTrue(nombresClase.get(0).equals(nombreClasePrimera));	
			
			//Probando Tablas
			tablas = NavajaConector.getInstance().getRaizMapeo().getTablas();
			Assert.assertTrue(tablas.size() == 1);
			TablaMapeo tablaMapeada = tablas.get(0);
			Assert.assertTrue(tablaMapeada.equals(tablaPrimera));
			Assert.assertTrue(tablaMapeada.getPrimaryKeyMapeo().equals(pk));			
		} catch (Exception e) {
			fail("Exception! " + e);
		} 
	}
	
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
}
