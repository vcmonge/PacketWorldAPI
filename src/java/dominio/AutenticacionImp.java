/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import dto.RSAutenticacionColaborador;
import java.util.HashMap;
import java.util.LinkedHashMap;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Conductor;
import utilidades.Seguridad;

/**
 *
 * @author julia
 */
public class AutenticacionImp {
    public static RSAutenticacionColaborador autenticacionConductor (String noPersonal, String password) {
        RSAutenticacionColaborador respuesta = new RSAutenticacionColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                HashMap<String, String> parametros = new LinkedHashMap<>();
                parametros.put("noPersonal", noPersonal);
                String hashPassword = Seguridad.hashearContrasenia(password);
                parametros.put("password", hashPassword);
                Colaborador conductor = conexionBD.selectOne("autenticacion.conductor", parametros);
                if (conductor != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Credenciales correctas: " + conductor.getNombre());
                    respuesta.setColaborador(conductor);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("Credenciales incorrectas.");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Lo sentimos, por el momento no hay conexión");
        }
        return respuesta;
    }
    
        public static RSAutenticacionColaborador iniciarSesionEscritorio(String noPersonal, String password) {
        RSAutenticacionColaborador respuesta = new RSAutenticacionColaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("noPersonal", noPersonal);
                String hashPassword = Seguridad.hashearContrasenia(password);
                parametros.put("password", hashPassword);

                // La consulta ya filtra a los conductores.
                Colaborador colaborador = conexionBD.selectOne("autenticacion.loginEscritorio", parametros);

                if (colaborador != null) {
                    // Si llegamos aquí, GARANTIZADO que NO es conductor.
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenido(a) " + colaborador.getNombre());
                    respuesta.setColaborador(colaborador);
                } else {
                    respuesta.setError(true);
                    // Mensaje genérico de seguridad
                    respuesta.setMensaje("Credenciales incorrectas o no tiene permisos para acceder.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error en el servidor.");
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }
    
}
