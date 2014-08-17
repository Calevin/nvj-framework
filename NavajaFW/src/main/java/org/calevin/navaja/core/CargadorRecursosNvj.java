package org.calevin.navaja.core;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sql.DataSource;

import org.calevin.navaja.excepciones.core.CargadorRecursosCarpetaConfVaciaException;
import org.calevin.navaja.excepciones.core.CargadorRecursosConexionFallidaException;
import org.calevin.navaja.excepciones.core.CargadorRecursosException;
import org.calevin.navaja.excepciones.core.CargadorRecursosNoExistenArchivosMapeoException;
import org.calevin.navaja.excepciones.mapeo.MapeoException;
import org.calevin.navaja.mapeo.Mapeador;
import org.calevin.navaja.sql.NavajaConector;
import org.calevin.navaja.util.NavajaConstantes;

/**
 * Clase utilizada para cargar los recursos necesarios.
 *
 * @author calevin
 */
public class CargadorRecursosNvj {

    /**
     * Carga los recursos que necesita NavajaFW
     *
     * @param carpetaArchivosConf Localizacion de la carpeta que aloja los
     * archivos de configuracion
     * @param recursosProyecto Implementacion de NvjManejadorRecursos con los
     * recursos que seran inicializados y que brindaran la conexion a la base de
     * datos.
     * @throws CargadorRecursosException 
     * @throws MapeoException 
     * @throws IOException 
     */
    public static void cargarRecursos(String carpetaArchivosConf, AdministradorRecursosNvjItf recursosProyecto) 
    		throws CargadorRecursosException, MapeoException, IOException{
        System.out.println("Comenzando la carga de NavajaFW");
        //Listo los archivos de configuracion
        String[] listaDeArchivos = new File(carpetaArchivosConf).list();
        
        if (listaDeArchivos == null) {
            throw new CargadorRecursosCarpetaConfVaciaException(carpetaArchivosConf);
        }

        //Se listan los archivos de la carpeta indicada
        ArrayList<String> archivosConf = new ArrayList<String>(Arrays.asList(listaDeArchivos));

        //Se los itera
        for (String archivoNombre : archivosConf){
            //Si algun archivo termina con _or.xml es un mapeo
            if (archivoNombre.endsWith(NavajaConstantes.GUION_BAJO_OR_PUNTO_XML)) {
                //Muestro que el archivo se esta leyendo
                System.out.println("Mapeando " + archivoNombre + "...");
                //Comienzo el mapeo
                Mapeador.mapearXml(carpetaArchivosConf + archivoNombre);
                System.out.println("Mapeado correctamente ");                
            }
        }
        
        if (Mapeador.getRaizMapeo() == null || Mapeador.getRaizMapeo().getTablas().isEmpty()) {
            throw new CargadorRecursosNoExistenArchivosMapeoException();        	
        }
        
        //Se setea la instancia NavajaConector de la app el mapeo recien generado
        NavajaConector.getInstance().setMapeoRaiz(Mapeador.getRaizMapeo());
        
        //Se inicializa los recursos segun lo implementado en el metodo sobrescrito
        recursosProyecto.iniciarRecursos();
        
        //Se setea la instancia NavajaConector de la app el DataSource para conectarse
        DataSource dataSource = recursosProyecto.proveerDataSource();
        
        if(dataSource == null) {
            throw new org.calevin.navaja.excepciones.core.CargadorRecursosDataSourceNuloException();
        }

        Connection conexion;
		try {
			conexion = dataSource.getConnection();
			
			if (conexion != null  && conexion.isValid(0)) {
		        NavajaConector.getInstance().setDataSource(dataSource);
			}
		} catch (SQLException e) {
			// TODO COMPLETAR
			throw new CargadorRecursosConexionFallidaException(e);
		}
    }
}
