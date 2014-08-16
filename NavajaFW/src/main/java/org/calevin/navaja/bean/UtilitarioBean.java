package org.calevin.navaja.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.calevin.navaja.excepciones.bean.BeanException;
import org.calevin.navaja.excepciones.bean.BeanInvocarGetterException;
import org.calevin.navaja.excepciones.bean.BeanInvocarSetterException;
import org.calevin.navaja.util.NavajaConstantes;
import org.calevin.navaja.util.NavajaStringUtil;

public class UtilitarioBean {

    /**
     * Invoca el getter de la instancia al atributo indicado
     * @param instancia Objeto del cual se invocara el getter
     * @param atributo Atributo del cual se invocara el getter
     * @return Valor del getter correpondiente
     * @throws BeanException 
     */
    public static Object invocarGetter(Object instancia, String atributo) throws BeanException {
        //Method metodo;
        Method metodos[];
        Class<?> clase = null;
        Object rta = null;
        String nombreMetodo = null;
        try {
            //Se toma la clase del objeto recibido
            clase = Class.forName(instancia.getClass().getName());

            //Se toman los metodos de la clase del objeto recibido
            metodos = clase.getMethods();

            for (Method metodo : metodos) {
            	nombreMetodo = metodo.getName();
            	
                //Si el nombre del metodo termina con el nombre de atributo y empieza con get
                if (nombreMetodo.endsWith(NavajaStringUtil.conmutarCaseChar(atributo, 0)) 
                		&& (nombreMetodo.startsWith(NavajaConstantes.GET))) {
                    //Se invoca a dicho getter y se guarda el resultado retornado
                    rta = metodo.invoke(instancia);
                }
			}
        } catch (Exception e) {
            throw new BeanInvocarGetterException(atributo, e);
        }

        return rta;
    }
    

    /**
     * Invoca de la instancia el setter del atributo indicado, y le envia
     * como parametro el valor
     *
     * @param instancia Objeto del cual se invocara el setter
     * @param atributo Atributo del cual se invocara el setter
     * @param valor Valor que se asignara mediante el setter
     */
    public static void invocarSetter(Object instancia, String atributo, Object valor) throws BeanException {
        Method metodos[];
        Class<?> clase = null;
        String nombreMetodo = null;

        try {
            //Se toma la clase del objeto recibido        	
            clase = Class.forName(instancia.getClass().getName());

            //Se toma los metodos de la clase del objeto enviado
            metodos = clase.getMethods();

            //Se recorre los metodos
            for (Method metodo : metodos) {

                //Se toma el nombre del metodo
                nombreMetodo = metodo.getName();

                //Si el final del nombre del metodo termina con el atributo 
                //enviado y empieza con set es el setter correspondiente
                if (nombreMetodo.endsWith(NavajaStringUtil.conmutarCaseChar(atributo, 0)) 
                		&& (nombreMetodo.startsWith(NavajaConstantes.SET))) {
                	//TODO INVOCAR CASTEAR EL VALOR SEGUN LO DEFINIDO
                    //ESTOY RECIBIENDO UN LONG DEL RESULSET Y DEBERIA SER INTEGER
                    
                    //Se invoca el metodo de ese objeto enviandole el valor
                    metodo.invoke(instancia, valor);
                    //Si se ejecuto salir
                    break;                    
                }
            }

        } catch (Exception e) {
            throw new BeanInvocarSetterException(atributo, valor, e);
        }
    }
    
    /**
     * Lista los atributos de una clase que tengan un metodo getter, sin contar
     * el metodo getClass
     *
     * @param clazz clase que de la cual se listara los atributos
     * @return ArrayList de Strig que tiene los atributos de la clase
     */
    public static ArrayList<String> listarAtts(Class<?> clazz) {
        ArrayList<String> atributos = new ArrayList<String>();
        //TODO ver: Class<?> clase;
        Method metodos[];

        //TODO ver: try {
            //TODO ver: clase = Class.forName(clazz.getName());
            //Se toma los metodos de la clase
            //TODO ver: metodos = clase.getMethods();
            metodos = clazz.getMethods();
            //Se recorre los metodos
            for (Method metodo : metodos) {
                //Se toma el nombre del metodo
                String nombreMetodo = metodo.getName();
                //Si comienza con get y no es gerClass
                if ((nombreMetodo.startsWith(NavajaConstantes.GET)) && (!nombreMetodo.equals("getClass"))) {
                    //Se toma el nombre del metodo quitandole el get inicial
                    String nombreAtributo = metodo.getName().substring(3);
                    //Se lo agrega al Array a retornar
                    atributos.add(NavajaStringUtil.conmutarCaseChar(nombreAtributo, 0));
                }
            }
        /*TODO ver: 
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR al obtener atributos" + e);
        }*/

        return atributos;
    }    
}
