/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import dto.RSAutenticacionConductor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Conductor;

/**
 *
 * @author julia
 */
public class AutenticacionImp {
    public static RSAutenticacionConductor autenticacionConductor (String noPersonal, String password) {
        RSAutenticacionConductor respuesta = new RSAutenticacionConductor();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                HashMap<String, String> parametros = new LinkedHashMap<>();
                parametros.put("noPersonal", noPersonal);
                parametros.put("password", password);
                Colaborador conductor = conexionBD.selectOne("autenticacion.conductor", parametros);
                if (conductor != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Credenciales correctas: " + conductor.getNombre());
                    respuesta.setConductor(conductor);
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
}
