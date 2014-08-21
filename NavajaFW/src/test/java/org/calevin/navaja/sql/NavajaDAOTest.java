package org.calevin.navaja.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.calevin.navaja.bean.NavajaBean;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.mapeo.Mapeador;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NavajaDAOTest {
	private static String QUERY_LIMPIAR_MOCK_TABLA = "delete from mock_tabla"; 
	private static String archivoMapeoPruebaDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao_or.xml";
	private static String archivoMapeoPropertiesDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao.properties";	

	@BeforeClass 
	static public void setUpClass() throws MapeoException {
		Mapeador.mapearXml(archivoMapeoPruebaDAO);
		NavajaConector.getInstance().setMapeoRaiz(Mapeador.getRaizMapeo());
		
        NavajaConector.getInstance().setDataSource(proveerDataSource());		
	}

	//TODO precisar el limpiado de la tabla
	@AfterClass
	static public void tearDownClass() throws SQLException {
		Connection con = proveerDataSource().getConnection();
	    PreparedStatement pstm = con.prepareStatement(QUERY_LIMPIAR_MOCK_TABLA);	
	    pstm.executeUpdate();			

	    con.close();
	    pstm.close();

		Mapeador.limpiarMapeo();
		NavajaConector.getInstance().setMapeoRaiz(null);			    
	}
	
	//TODO agregar el tearDown y el setUp
	
	//TODO agregar la comprobacion
	@Test
	public void insertarmeTest(){
		MockClase mockAinsertar = new MockClase("insertarmeTest", 1);
		
		mockAinsertar.insertarme();
	}
	
	//inner class para test
	public class MockClase  extends NavajaDAO implements NavajaBean {
		private String atributoString;
		private Integer atributoInteger;

		public MockClase() {
		}

		public MockClase(String atributoString, Integer atributoInteger) {
			this.atributoString = atributoString;
			this.atributoInteger = atributoInteger;
		}

		public String getAtributoString() {
			return atributoString;
		}
		
		public void setAtributoString(String atributoString) {
			this.atributoString = atributoString;
		}
		
		public Integer getAtributoInteger() {
			return atributoInteger;
		}
		
		public void setAtributoInteger(Integer atributoInteger) {
			this.atributoInteger = atributoInteger;
		}
	}

	private static DataSource proveerDataSource() {
        DataSource dataSource = null;
        Properties propertiesBDD = new Properties();
        try {
            propertiesBDD.load(new FileInputStream(archivoMapeoPropertiesDAO));

            dataSource = BasicDataSourceFactory.createDataSource(propertiesBDD);
        } catch (IOException ex) {
            System.out.println("Error al cargar archivo: " + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex);
        }
            return dataSource;
	}
}
