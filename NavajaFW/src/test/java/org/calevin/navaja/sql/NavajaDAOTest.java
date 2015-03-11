package org.calevin.navaja.sql;

import static org.junit.Assert.*;

import java.io.FileInputStream;
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
import org.calevin.navaja.excepciones.sql.PkInvalidaException;
import org.calevin.navaja.mapeo.Mapeador;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class NavajaDAOTest {
	
	private static String MOCK_TABLA = "mock_tabla_prueba_dao";

	private static String VARCHAR_INSERT_OK = "insertTest";	
	private static String VARCHAR_BORRARME_OK= "borrarmeOk";
	private static String VARCHAR_UPDATE_OK= "updateOk";	
	
	private static String QUERY_LIMPIAR_MOCK_TABLA_INSERT_CASO_OK = "delete from "+ MOCK_TABLA +" where " 
			+ "atributo_varchar = '" + VARCHAR_INSERT_OK +"' AND atributo_int = 1";
	
	private static String QUERY_LIMPIAR_MOCK_TABLA_BORRARME_OK = "delete from " + MOCK_TABLA + " where "
			+ "atributo_varchar = '" + VARCHAR_BORRARME_OK +"' AND atributo_int = 2";
	
	private static String QUERY_LIMPIAR_MOCK_TABLA_ACTUALIZAME_OK = "delete from " + MOCK_TABLA + " where " 
			+ "atributo_varchar = '" + VARCHAR_UPDATE_OK + "' AND atributo_int = 3";	
	
	private static String archivoMapeoPruebaDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao_or.xml";
	private static String archivoMapeoPropertiesDAO = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebadao/prueba_dao.properties";	

	@BeforeClass 
	static public void setUpClass() throws MapeoException, Exception {
		Mapeador.mapearXml(archivoMapeoPruebaDAO);
		NavajaConector.getInstance().setMapeoRaiz(Mapeador.getRaizMapeo());
		
        NavajaConector.getInstance().setDataSource(proveerDataSource());		
	}

	@AfterClass
	static public void tearDownClass() throws SQLException, CerrarRecursoException {
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_INSERT_CASO_OK);
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_BORRARME_OK);
		NavajaConector.ejecutarUpdate(QUERY_LIMPIAR_MOCK_TABLA_ACTUALIZAME_OK);
		
		Mapeador.limpiarMapeo();
		NavajaConector.getInstance().setMapeoRaiz(null);
	}

	@Test
	public void insertarmeTestCasoCorrecto(){
		try {
			MockClase mockAinsertar = new MockClase(VARCHAR_INSERT_OK, 1, 2, 3, 4);

			mockAinsertar.insertarme();
			
			if(!existeMockEnLaBase(mockAinsertar)){
				fail("No se encontro el registro insertado");
			}

		} catch (MapeoClaseNoExisteException | SQLException | BeanException | CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		}
	}

	@Test
	public void borrarmeTestCasoCorrecto(){
		try {
		MockClase mockAborrar = new MockClase(VARCHAR_BORRARME_OK, 2, null, null, null);
		
		insertarMock(mockAborrar);
		
		if(!existeMockEnLaBase(mockAborrar)){
			fail("No se encontro el registro esperado");
		}

		mockAborrar.borrarme();
		
		if(existeMockEnLaBase(mockAborrar)){
			fail("Se encontro el registro esperado que deberia haber sido borrado");
		}
		
		} catch (MapeoClaseNoExisteException | SQLException | BeanException | CerrarRecursoException | PkInvalidaException e) {
			fail("Excepcion inesperada " + e);
		}
	}

	@Test (expected = PkInvalidaException.class)
	public void borrarmeTestCasoPkInvalidaException(){
		try {

		MockClase mockAborrarConPkInvalida = new MockClase(null, null, null, null, null);

		mockAborrarConPkInvalida.borrarme();

		} catch (MapeoClaseNoExisteException | SQLException | BeanException | CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		} catch (PkInvalidaException e) {
			throw e;
		}
	}
	
	@Test
	public void actualizameTestCasoCorrecto(){
		try {
			MockClase mockUpdate = new MockClase(VARCHAR_UPDATE_OK, 3, 12, 13, 14);
			
			insertarMock(mockUpdate);
			
			if(!existeMockEnLaBase(mockUpdate)){
				fail("No se encontro registro esperado");
			}

			mockUpdate.setSegundoAtributoInteger(222);
			mockUpdate.setTercerAtributoInteger(333);
			mockUpdate.setCuartoAtributoInteger(444);
			
			mockUpdate.actualizame();

			if(!existeMockEnLaBase(new MockClase(VARCHAR_UPDATE_OK, 3, 222, 333, 444))){
				fail("No se encontro registro esperado");
			}			

		} catch (MapeoClaseNoExisteException | BeanException | SQLException | CerrarRecursoException e) {
			fail("Excepcion inesperada " + e);
		}		
	}

	@Test
	public void isPkValidaCasoReturnTrue(){
		try {
			MockClase mockConPkValida = new MockClase("varcharPK", 0, null, null, null);
			Assert.assertTrue(mockConPkValida.isPkValida());
		} catch (MapeoClaseNoExisteException | BeanException e) {
			fail("Excepcion inesperada " + e);
		}
	}
	
	@Test
	public void isPkValidaCasoReturnFalse(){
		try {
			MockClase mockConPkInvalida = new MockClase(null, 0, null, null, null);
			Assert.assertFalse(mockConPkInvalida.isPkValida());
			mockConPkInvalida = new MockClase("varcharPK", null, null, null, null);
			Assert.assertFalse(mockConPkInvalida.isPkValida());

		} catch (MapeoClaseNoExisteException | BeanException e) {
			fail("Excepcion inesperada " + e);
		}
	}
	
	//inner class para test
	public  static class MockClase  extends NavajaDAO implements NavajaBean {
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
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime
					* result
					+ ((atributoInteger == null) ? 0 : atributoInteger
							.hashCode());
			result = prime
					* result
					+ ((atributoString == null) ? 0 : atributoString.hashCode());
			result = prime
					* result
					+ ((cuartoAtributoInteger == null) ? 0
							: cuartoAtributoInteger.hashCode());
			result = prime
					* result
					+ ((segundoAtributoInteger == null) ? 0
							: segundoAtributoInteger.hashCode());
			result = prime
					* result
					+ ((tercerAtributoInteger == null) ? 0
							: tercerAtributoInteger.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			MockClase other = (MockClase) obj;
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

	}

	//METODOS UTILITARIOS
	/**
	 * Provee el datasource para conectarse a la base de testeo
	 * @return datasource para conectarse a la base de testeo
	 * @throws Exception
	 */
	private static DataSource proveerDataSource() throws Exception {
        DataSource dataSource = null;
        Properties propertiesBDD = new Properties();
        
        propertiesBDD.load(new FileInputStream(archivoMapeoPropertiesDAO));

        dataSource = BasicDataSourceFactory.createDataSource(propertiesBDD);

        return dataSource;
	}
	
	/**
	 * Inserta en la base de datos el mock enviado por parametros
	 * @param mock a insertar en la base
	 * @throws SQLException
	 * @throws CerrarRecursoException
	 */
	private static void insertarMock(MockClase mock) throws SQLException, CerrarRecursoException{
		
		String insert = "INSERT INTO " + MOCK_TABLA;
		String insertCampos = " (atributo_varchar, atributo_int";
		String insertValores = "VALUES ('" 
				+ mock.getAtributoString() + "', "
				+ mock.getAtributoInteger();

		if (mock.getSegundoAtributoInteger() != null){
			insertCampos += ", segundo_atributo_int";
			insertValores += ", " + mock.getSegundoAtributoInteger();
		}
		
		if(mock.getTercerAtributoInteger() != null){
			insertCampos += ", tercer_atributo_int";
			insertValores += ", " + mock.getTercerAtributoInteger();
		}
		
		if(mock.getCuartoAtributoInteger() != null){
			insertCampos += ", cuarto_atributo_int";
			insertValores += ", " + mock.getCuartoAtributoInteger();
		}

		insert += insertCampos + ") ";
		insert += insertValores + ") ";
		
		NavajaConector.ejecutarUpdate(insert);		
	}
	
	/**
	 * Comprueba que este mock esta en la base de datos
	 * @param mock registro a comprobrar en la base
	 * @return true en caso de que ese mock este en la base de datos, false en caso contrario
	 * @throws CerrarRecursoException 
	 * @throws SQLException 
	 * @throws MapeoClaseNoExisteException 
	 */
	private static boolean existeMockEnLaBase(MockClase mock) throws SQLException, CerrarRecursoException, MapeoClaseNoExisteException {

		String consulta = "SELECT * FROM " + MOCK_TABLA + " WHERE " 
				+ "atributo_varchar='" + mock.getAtributoString() + "' "
				+ "AND atributo_int=" + mock.getAtributoInteger();

		ResultSet rsConsulta = NavajaConector.ejecutarQuery(consulta);
		
		MockClase mockResultaQuery = new MockClase();
		
		if (rsConsulta.next()){
			mockResultaQuery.setAtributoString((String) rsConsulta.getObject("atributo_varchar"));
			mockResultaQuery.setAtributoInteger((Integer) rsConsulta.getObject("atributo_int"));
			mockResultaQuery.setSegundoAtributoInteger((Integer) rsConsulta.getObject("segundo_atributo_int"));
			mockResultaQuery.setTercerAtributoInteger((Integer) rsConsulta.getObject("tercer_atributo_int"));
			mockResultaQuery.setCuartoAtributoInteger((Integer) rsConsulta.getObject("cuarto_atributo_int"));			
		}

		NavajaConector.cerrarRecurso(rsConsulta);
		
		return mock.equals(mockResultaQuery);
	}	
}
