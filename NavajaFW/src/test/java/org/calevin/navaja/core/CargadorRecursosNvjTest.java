package org.calevin.navaja.core;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class CargadorRecursosNvjTest {
	
    public static final String CARPETA_CARGADOR_RECURSOS_TEST = "src/test/java/org/calevin/navaja/core/";
	private String uriPropertiesBDD = null;	
	private AdministradorRecursosMock recursosProyecto = null;
	
	@Before
	public void setUp() throws Exception {
		uriPropertiesBDD = CARPETA_CARGADOR_RECURSOS_TEST + "prueba_cargador_recursos.properties";
		recursosProyecto = new AdministradorRecursosMock();
		recursosProyecto.setUriPropertiesBDD(uriPropertiesBDD);
	}

	@Test
	public void cargarRecursosTest(){
		try {
			CargadorRecursosNvj.cargarRecursos(CARPETA_CARGADOR_RECURSOS_TEST, recursosProyecto);
		} catch (Exception e) {
			fail("Exception! " + e);
		} 
	}
}
