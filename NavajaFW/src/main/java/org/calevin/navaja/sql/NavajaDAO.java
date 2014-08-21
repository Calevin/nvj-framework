package org.calevin.navaja.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.calevin.navaja.bean.UtilitarioBean;
import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.mapeo.MapeoClaseNoExisteException;
import org.calevin.navaja.mapeo.CampoMapeo;
import org.calevin.navaja.mapeo.TablaMapeo;

public class NavajaDAO {

    protected TablaMapeo tablaMapeo = null;
   
    public NavajaDAO() {
    	super();
    }
    
    private void cargarTabla() throws MapeoClaseNoExisteException{
    	if (this.tablaMapeo == null){
    		this.tablaMapeo = NavajaConector.getInstance().getRaizMapeo().getTablaPorNombreClase(this.getClass().getName());    		
    	}
    }
    
    public void insertarme() {
    	try {
			cargarTabla();
		} catch (MapeoClaseNoExisteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
        Connection con = null;
        PreparedStatement pstm = null;
        
        //Se toma la Tabla de dicho bean a partir del mapeo
        //Tabla tabla = this.mapeoRaiz.getTablaPorNombreClase(this.getClass().getName());        
        
        //Se toma la cantidad de campos
        int cantidadCampos = tablaMapeo.getCampos().size();
        String tablaNombre = tablaMapeo.getNombre();        
        
        //Se crea el insert a partir de nombre de la tabla y de listar todos
        //sus campo
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

        try {
			con = NavajaConector.getInstance().getDataSource().getConnection();
	        pstm = con.prepareStatement(insercion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
            try {
				pstm.setObject(j, UtilitarioBean.invocarGetter(this, att));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BeanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        

        System.out.println("\nLa Insercion " + insercion + ") ");

        System.out.println("Por ejecutar " + pstm.toString());

        try {
			System.out.println("Cambios por la ejecucion: " + pstm.executeUpdate());
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
    
    //PARA CERRAR RECURSOS
    protected void cerrarRecurso(Connection conn) throws SQLException {
        if (null != conn) {
                conn.close();
        }
    }

    protected void cerrarRecurso(Statement stm) throws SQLException {
        if (null != stm) {
                stm.close();
        }
    }

    protected void cerrarRecurso(ResultSet rs) throws SQLException {
        if (null != rs) {
                rs.close();
        }
    }

    protected void cerrarRecursos(Connection conn, Statement stm, ResultSet rs)
            throws SQLException {
        this.cerrarRecurso(rs);
        this.cerrarRecurso(stm);
        this.cerrarRecurso(conn);
    }
}
