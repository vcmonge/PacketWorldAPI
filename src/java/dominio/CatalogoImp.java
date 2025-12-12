/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.EstatusEnvio;
import pojo.Rol;
import pojo.TipoUnidad;

/**
 *
 * @author julia
 */
public class CatalogoImp {
    public static List<Rol> obtenerRolesSistema() {
        List<Rol> roles = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                roles = conexionBD.selectList("obtener-rol");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return roles;
    }
    
    public static List<EstatusEnvio> obtenerEstatusEnviosSistema() {
        List<EstatusEnvio> estatusEnvios = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                estatusEnvios = conexionBD.selectList("obtener-estatus-envio");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return estatusEnvios;
    }
    
    public static List<TipoUnidad> obtenerTipoUnidadesSistema() {
        List<TipoUnidad> tipoUnidades = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                tipoUnidades = conexionBD.selectList("obtener-tipo-unidad");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return tipoUnidades;
    }
    
    
}
