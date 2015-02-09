package org.calevin.navaja.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.utiltest.ConstantesParaTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class NavajaConectorTest {
	private static String archivoMapeoProperties = ConstantesParaTests.CARPETA_ARCHIVOS_TEST + "pruebaconector/prueba_conector.properties";
	private static String QUERY_SELECT_1 = "select database();";
	private static String QUERY_CREATE_TABLE = "CREATE TABLE test_create_tabla (columna int)";
	private static String QUERY_DROP_TABLE_TEST_CREATE = "DROP TABLE test_create_tabla;";
	private static String DATABASE_NOMBRE = "test_database";
	private static String COLUMNA_CONSULTA_DATABASE = "database()";
	
	@BeforeClass 
	static public void setUpClass() throws MapeoException {
        NavajaConector.getInstance().setDataSource(proveerDataSource());
	}
	
	@AfterClass
	static public void tearDownClass() throws SQLException {
        NavajaConector.getInstance().setDataSource(null);
        
        dropTablaTestCreate();
	}
		
	@Test
	public void ejecutarQueryTest(){
		ResultSet rs = NavajaConector.ejecutarQuery(QUERY_SELECT_1);
		
		try {
			rs.next();
			
			Assert.assertTrue(DATABASE_NOMBRE.equals((String)rs.getObject(COLUMNA_CONSULTA_DATABASE)));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			cerrarRecurso(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void ejecutarQueryCreateTableTest(){
		NavajaConector.ejecutarUpdate(QUERY_CREATE_TABLE);
	}
	
	private static void dropTablaTestCreate(){
        Connection con = null;
        PreparedStatement pstm = null;
        try {
        con = proveerDataSource().getConnection();
        pstm = con.prepareStatement(QUERY_DROP_TABLE_TEST_CREATE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
        System.out.println("Por ejecutar " + pstm.toString());
        pstm.executeUpdate();
        System.out.println("Ejecutado ");
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
	}
	
	private static DataSource proveerDataSource() {
        DataSource dataSource = null;
        Properties propertiesBDD = new Properties();
        try {
            propertiesBDD.load(new FileInputStream(archivoMapeoProperties));

            dataSource = BasicDataSourceFactory.createDataSource(propertiesBDD);
        } catch (IOException ex) {
            System.out.println("Error al cargar archivo: " + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex);
        }
            return dataSource;
	}
	
	//PARA CERRAR RECURSOS
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

	private static void cerrarRecurso(ResultSet rs) throws SQLException {
        if (null != rs) {
                rs.close();
        }
    }
}
