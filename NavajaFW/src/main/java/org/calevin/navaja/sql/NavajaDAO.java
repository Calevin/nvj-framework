package org.calevin.navaja.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.calevin.navaja.bean.UtilitarioBean;
import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.excepciones.sql.CerrarRecursoException;
import org.calevin.navaja.mapeo.CampoMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;

public class NavajaDAO {

    protected TablaMapeo tablaMapeo = null;
   
    public NavajaDAO() {
    	super();
    }
    
    /**
     * Define el atributo tablaMapeo de la clase a partir del nombre de la misma, obteniendolo del mapeo general desde NavajaConector.
     * @throws MapeoClaseNoExisteException
     */
    private void cargarTabla() throws MapeoClaseNoExisteException{
    	if (this.tablaMapeo == null){
    		this.tablaMapeo = NavajaConector.getInstance().getRaizMapeo().getTablaPorNombreClase(this.getClass().getName());    		
    	}
    }
    
    /**
     * inserta en la base de datos los valores actuales del objeto.
     * @throws MapeoClaseNoExisteException 
     * @throws SQLException 
     * @throws BeanException 
     * @throws CerrarRecursoException 
     */
    public void insertarme() throws MapeoClaseNoExisteException, SQLException, BeanException, CerrarRecursoException {
        
    	//Se define el TablaMapeo de dicho bean a partir del mapeo
    	cargarTabla();
    	
        Connection con = null;
        PreparedStatement pstm = null;
         
        //Se obtiene la cantidad de campos
        int cantidadCampos = tablaMapeo.getCampos().size();
        //Se obtiene el nombre de la tabla
        String tablaNombre = tablaMapeo.getNombre();        
        
        //Se crea el insert a partir de nombre de la tabla y de listar todos sus  campos
        String insercion = "";

        String insercionInsertInto = "INSERT INTO " + tablaNombre;
        String insercionColumnas = " (";
        String insercionValues = "VALUES (";
        //Se obtiene los campos de la tabla
        ArrayList<String> campos = listaCamposParaQuery();
        //El insert tiene la forma:
        //INSERT INTO nombre_tabla
        //(columna1, columna2,columna3)
        //VALUES (?, ?, ?)
        for (int i = 0; i < cantidadCampos; i++) {
        	//Despues del primer item se ingresa una coma antes de cada nuevo item
            if (i != 0){
            	insercionColumnas += ", ";
            	insercionValues += ", ";
            }
            insercionColumnas += campos.get(i);
       		insercionValues += "?";
        }
        insercionColumnas += ") ";
        insercionValues += ") ";
        //Se completa el INSERT con todas las partes de la query
        insercion = insercionInsertInto + insercionColumnas + insercionValues;
        
		con = NavajaConector.getInstance().getDataSource().getConnection();
	    pstm = con.prepareStatement(insercion);
	    //Se setean los parametros obtienendo los atributos de la instancia
	    for (int i = 0; i < cantidadCampos; i++) {
	    	//a partir del campo se obtiene el valor de dicho atributo
	    	String campo = campos.get(i);
	    	String atributo = tablaMapeo.getCampoMapeoPorNombre(campo).getNombreComoAtributo();
	    	//Los parametros del PrepareStatement se inician en 1 por eso se usa i+1
	    	pstm.setObject(i+1, UtilitarioBean.invocarGetter(this, atributo));	
	    }
	    
        System.out.println("\nInsercion: " + insercion + ") ");

        System.out.println("Se ejecutara: " + pstm.toString());

		System.out.println("Cambios por la ejecucion: " + pstm.executeUpdate());

		NavajaConector.cerrarRecurso(con);
		NavajaConector.cerrarRecurso(pstm);
    }

    /**
     * Lista todos los campos de una tabla
     *
     * @param tabla tabla de la cual listara los campos
     * @return un ArrayList<String> con los campos de la tabla
     */
    public ArrayList<String> listaCamposParaQuery() {
    	ArrayList<String> campos = new ArrayList<String>();

        int cantidadCampos = tablaMapeo.getCampos().size();
        
        for (int i = 0; i < cantidadCampos; i++) {
            CampoMapeo campo = tablaMapeo.getCampos().get(i);
            campos.add(campo.getNombre());
        }

        return campos;
    }    

}
