package org.calevin.navaja.core;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSource;
import org.calevin.navaja.mapeo.CampoMapeo;
import org.calevin.navaja.mapeo.PrimaryKeyMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;
import org.calevin.navaja.sql.NavajaConector;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class CargadorRecursosNvjTest {
	
    private String CARPETA_CARGADOR_RECURSOS_TEST = "src/test/java/org/calevin/navaja/core/";
    private String nombreClasePrimera = "org.empresa.beans.PruebaCargador";    
	private String uriPropertiesBDD = null;	
	private AdministradorRecursosMock recursosProyecto = null;
    private ArrayList<String> nombresClase = null;	
    private ArrayList<TablaMapeo> tablas = null;
    private TablaMapeo tablaPrimera = null;
    private PrimaryKeyMapeo pk = null;
    
	@Before
	public void setUp() throws Exception {
		nombresClase = new ArrayList<String>();	
		tablas = new ArrayList<TablaMapeo>();

		//Tablas
		tablaPrimera = new TablaMapeo();
		tablaPrimera.setNombre("pruebaCargador");
		tablaPrimera.setNombreComoClase(nombreClasePrimera);
		tablaPrimera.getCampos().add(0, new CampoMapeo("id","idCargador"));
		tablaPrimera.getCampos().add(1, new CampoMapeo("des","desCargador"));
		pk = new PrimaryKeyMapeo();
		pk.getCampos().add(new CampoMapeo("idCargador",null));
		tablaPrimera.setPrimaryKeyMapeo(pk);
		
		uriPropertiesBDD = CARPETA_CARGADOR_RECURSOS_TEST + "prueba_cargador_recursos.properties";
		recursosProyecto = new AdministradorRecursosMock();
		recursosProyecto.setUriPropertiesBDD(uriPropertiesBDD);
	}

	@Test
	public void cargarRecursosCasoCorrectoTest(){
		try {
			CargadorRecursosNvj.cargarRecursos(CARPETA_CARGADOR_RECURSOS_TEST, recursosProyecto);
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
}
