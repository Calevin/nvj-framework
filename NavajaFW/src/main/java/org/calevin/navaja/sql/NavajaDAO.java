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
        insercion += "INSERT INTO " + tablaNombre + " ";
        insercion += " (" + listaCamposParaQuery(tablaMapeo) + ") ";
        insercion += "VALUES (";
        for (int i = 0; i < cantidadCampos; i++) {
            insercion = insercion + "?";
            if (i + 1 < cantidadCampos) {
                insercion += ", ";
            }
        }
        insercion += ") "; 

		con = NavajaConector.getInstance().getDataSource().getConnection();
	    pstm = con.prepareStatement(insercion);

        //Se toma los atributos del bean
        ArrayList<String> atributos = UtilitarioBean.listarAtts(this.getClass());        

        // TODO revisar recorrido
        
        //Se recorre la cantidad de campos
        //Los campos listados se reciben del ultimo al primero
        //Por lo cual se toman de forma inversa
        for (int i = cantidadCampos, j = 1; i > 0; i--, j++) {
            //Por cada campo se toma un atributo
            String att = atributos.get(i-1);
            //Se setea el valor de dicho atributo al sentencia insert
			pstm.setObject(j, UtilitarioBean.invocarGetter(this, att));
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
     * @return un String con los campos de la tabla separados por coma
     */
    //TODO revisar la iteracion
    public String listaCamposParaQuery(TablaMapeo tabla) {
        String rta = "";
        int cantidadCampos = tabla.getCampos().size();

        for (int i = cantidadCampos; i > 0; i--) {
            CampoMapeo campo = tabla.getCampos().get(i - 1);
            rta += campo.getNombre();
            //SI AUN QUEDAN CAMPOS LE AGREGO UNA COMA
            if (i - 2 >= 0) {
                rta += ", ";
            }
        }

        return rta;
    }    

}
