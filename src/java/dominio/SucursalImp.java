/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Sucursal;
import utilidades.Constantes;

/**
 *
 * @author HECTO
 */
public class SucursalImp {

    public static List<Sucursal> obtenerSucursales() {
        List<Sucursal> sucursales = null;
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                sucursales = conexion.selectList("sucursal.obtener-sucursales");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return sucursales;
    }
    
    public static List<Sucursal> obtenerSucursalesActivas() {
        List<Sucursal> sucursales = null;
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                sucursales = conexion.selectList("sucursal.obtener-activas");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return sucursales;
    }

    public static Respuesta registrarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasDireccion = conexion.insert("sucursal.registrar-direccion", sucursal);
                
                if (filasDireccion > 0 && sucursal.getIdDireccion() != null) {
                    int filasSucursal = conexion.insert("sucursal.registrar-sucursal", sucursal);
                    
                    if (filasSucursal > 0) {
                        conexion.commit();
                        respuesta.setError(false);
                        respuesta.setMensaje("Sucursal registrada correctamente");
                    } else {
                        respuesta.setMensaje("Error al registrar la información de la sucursal");
                    }
                } else {
                    respuesta.setMensaje("Error al registrar la dirección de la sucursal");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }

    public static Respuesta editarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasSucursal = conexion.update("sucursal.editar-sucursal", sucursal);
                int filasDireccion = conexion.update("sucursal.editar-direccion", sucursal);
               
                if (filasSucursal > 0 || filasDireccion > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal editada correctamente");
                } else {
                    respuesta.setMensaje("No se pudo actualizar la información de la sucursal. Verifique los IDs.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }

    public static Respuesta bajarSucursal(Integer idSucursal) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasAfectadas = conexion.update("sucursal.baja-sucursal", idSucursal);
                
                if (filasAfectadas > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal dada de baja correctamente");
                } else {
                    respuesta.setMensaje("No se encontró la sucursal con el ID proporcionado");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }
    
    public static Sucursal buscarSucursalPorId(int idSucursal) {
        Sucursal sucursal = null;
        SqlSession conexion = MyBatisUtil.getSession();
        if (conexion != null) {
            try {
                sucursal = conexion.selectOne("sucursal.buscar-sucursal-id", idSucursal);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return sucursal;
    }
}