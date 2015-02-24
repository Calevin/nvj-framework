package org.calevin.navaja.sql;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.calevin.navaja.bean.NavajaBean;
import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.excepciones.sql.CerrarRecursoException;
import org.calevin.navaja.mapeo.Mapeador;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class NavajaDAOTest {
	
	private static String INSERT_VALOR_VARCHAR= "insertTest";	
	private static String INSERT_VALOR_VARCHAR_TEST_BORRARME= "insertPk";
	private static int INSERT_VALOR_INT_TEST_BORRARME= 111;
	private static String QUERY_LIMPIAR_MOCK_TABLA_INSERT_VALOR_VARCHAR = "delete from mock_tabla_prueba_dao where atributo_varchar = '" + INSERT_VALOR_VARCHAR +"'";
	private static String QUERY_LIMPIAR_MOCK_TABLA_TEST_DELETE = "delete from mock_tabla_prueba_dao" 
			+ " where atributo_varchar = '" + INSERT_VALOR_VARCHAR_TEST_BORRARME +"' AND atributo_int = " + INSERT_VALOR_INT_TEST_BORRARME;	
	private static String QUERY_COMPROBACION_INSERT_VALOR_VARCHAR = "select * from mock_tabla_prueba_dao where atributo_varchar = '" + INSERT_VALOR_VARCHAR +"'";	
	private static String archivoMapeoPruebaDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao_or.xml";
	private static String archivoMapeoPropertiesDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao.properties";	

	@BeforeClass 
	static public void setUpClass() throws MapeoException {
		Mapeador.mapearXml(archivoMapeoPruebaDAO);
		NavajaConector.getInstance().setMapeoRaiz(Mapeador.getRaizMapeo());
		
        NavajaConector.getInstance().setDataSource(proveerDataSource());		
	}

	@AfterClass
	static public void tearDownClass() throws SQLException, CerrarRecursoException {
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_INSERT_VALOR_VARCHAR);
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_TEST_DELETE);
				
		Mapeador.limpiarMapeo();
		NavajaConector.getInstance().setMapeoRaiz(null);
	}

	//TODO agregar casos negativos/comprobar excepciones
	@Test
	public void insertarmeTestCasoCorrecto(){
		try {
			MockClase mockAinsertar = new MockClase(INSERT_VALOR_VARCHAR, 1, 2, 3, 4);

			mockAinsertar.insertarme();

			ResultSet resultComprobacion = NavajaConector.ejecutarQuery(QUERY_COMPROBACION_INSERT_VALOR_VARCHAR);
			if(resultComprobacion.next()){
				MockClase mockParaComprobacion = new MockClase(INSERT_VALOR_VARCHAR, 1, 2, 3, 4);
				mockParaComprobacion.setAtributoString(resultComprobacion.getString("atributo_varchar"));
				mockParaComprobacion.setAtributoInteger(resultComprobacion.getInt("atributo_int"));  
				mockParaComprobacion.setSegundoAtributoInteger(resultComprobacion.getInt("segundo_atributo_int")); 
				mockParaComprobacion.setTercerAtributoInteger(resultComprobacion.getInt("tercer_atributo_int")); 
				mockParaComprobacion.setCuartoAtributoInteger(resultComprobacion.getInt("cuarto_atributo_int")); 
				
				Assert.assertTrue(mockAinsertar.equals(mockParaComprobacion));
				} else {
				fail("No se encontraron registros para comprobar el insert");
			}

		} catch (MapeoClaseNoExisteException e) {
			fail("Excepcion inesperada " + e);
		} catch (SQLException e) {
			fail("Excepcion inesperada " + e);
		} catch (BeanException e) {
			fail("Excepcion inesperada " + e);
		} catch (CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		}
	}

	@Test
	public void borrarmeTestCasoCorrecto(){
		try {
		String insert = "INSERT INTO mock_tabla_prueba_dao (atributo_varchar, atributo_int) VALUES ('"
				+ INSERT_VALOR_VARCHAR_TEST_BORRARME + "', " + INSERT_VALOR_INT_TEST_BORRARME +")";
		
		NavajaConector.ejecutarUpdate(insert);
		String consulta = "SELECT * FROM mock_tabla_prueba_dao WHERE " 
				+  "atributo_varchar='"+ INSERT_VALOR_VARCHAR_TEST_BORRARME + "' AND "
				+ "atributo_int="+ INSERT_VALOR_INT_TEST_BORRARME;

		ResultSet rsConsulta = NavajaConector.ejecutarQuery(consulta);
		if (rsConsulta.next()){
			Assert.assertTrue(rsConsulta.getString("atributo_varchar").equals(INSERT_VALOR_VARCHAR_TEST_BORRARME));
			Assert.assertTrue(rsConsulta.getInt("atributo_int") == INSERT_VALOR_INT_TEST_BORRARME);
		} else {
			fail("No se encontro registro en la consulta de borrarmeTest antes del borrado");
		}
		
		MockClase mockAborrar = new MockClase(INSERT_VALOR_VARCHAR_TEST_BORRARME, INSERT_VALOR_INT_TEST_BORRARME, null, null, null);

		mockAborrar.borrarme();
		
		rsConsulta = NavajaConector.ejecutarQuery(consulta);
		if (rsConsulta.next()){
			fail("Se encontro registro en la consulta de borrarmeTest despues del borrado");
		}
		
		} catch (MapeoClaseNoExisteException e) {
			fail("Excepcion inesperada " + e);
		} catch (SQLException e) {
			fail("Excepcion inesperada " + e);
		} catch (BeanException e) {
			fail("Excepcion inesperada " + e);
		} catch (CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		}
	}
	
	//inner class para test
	public class MockClase  extends NavajaDAO implements NavajaBean {
		private String atributoString;
		private Integer atributoInteger;
		private Integer segundoAtributoInteger;
		private Integer tercerAtributoInteger;
		private Integer cuartoAtributoInteger;		
		
		public MockClase() throws MapeoClaseNoExisteException {
			super();
		}

		public MockClase(String atributoString, Integer atributoInteger,
				Integer segundoAtributoInteger, Integer tercerAtributoInteger,
				Integer cuartoAtributoInteger) throws MapeoClaseNoExisteException {
			super();
			this.atributoString = atributoString;
			this.atributoInteger = atributoInteger;
			this.segundoAtributoInteger = segundoAtributoInteger;
			this.tercerAtributoInteger = tercerAtributoInteger;
			this.cuartoAtributoInteger = cuartoAtributoInteger;
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

		public Integer getSegundoAtributoInteger() {
			return segundoAtributoInteger;
		}

		public void setSegundoAtributoInteger(Integer segundoAtributoInteger) {
			this.segundoAtributoInteger = segundoAtributoInteger;
		}

		public Integer getTercerAtributoInteger() {
			return tercerAtributoInteger;
		}

		public void setTercerAtributoInteger(Integer tercerAtributoInteger) {
			this.tercerAtributoInteger = tercerAtributoInteger;
		}

		public Integer getCuartoAtributoInteger() {
			return cuartoAtributoInteger;
		}

		public void setCuartoAtributoInteger(Integer cuartoAtributoInteger) {
			this.cuartoAtributoInteger = cuartoAtributoInteger;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			//TablaMapeo puede no estar instanciado y por lo tanto no seran iguales
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			MockClase other = (MockClase) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (atributoInteger == null) {
				if (other.atributoInteger != null)
					return false;
			} else if (!atributoInteger.equals(other.atributoInteger))
				return false;
			if (atributoString == null) {
				if (other.atributoString != null)
					return false;
			} else if (!atributoString.equals(other.atributoString))
				return false;
			if (cuartoAtributoInteger == null) {
				if (other.cuartoAtributoInteger != null)
					return false;
			} else if (!cuartoAtributoInteger
					.equals(other.cuartoAtributoInteger))
				return false;
			if (segundoAtributoInteger == null) {
				if (other.segundoAtributoInteger != null)
					return false;
			} else if (!segundoAtributoInteger
					.equals(other.segundoAtributoInteger))
				return false;
			if (tercerAtributoInteger == null) {
				if (other.tercerAtributoInteger != null)
					return false;
			} else if (!tercerAtributoInteger
					.equals(other.tercerAtributoInteger))
				return false;
			return true;
		}

		private NavajaDAOTest getOuterType() {
			return NavajaDAOTest.this;
		}
	}

	//METODO UTILITARIO
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
