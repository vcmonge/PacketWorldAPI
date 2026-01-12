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
                
                int filasSucursal = conexion.insert("sucursal.registrar-sucursal", sucursal);
                
                if (filasSucursal > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal registrada correctamente");
                } else {
                    respuesta.setMensaje("Error al registrar la información de la sucursal");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setMensaje("Error al procesar el registro. Verifique que los datos sean correctos.");
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
                if (!existeSucursal(conexion, sucursal.getIdSucursal())) {
                    respuesta.setMensaje("La sucursal que intenta editar no existe.");
                    return respuesta;
                }

                int filasSucursal = conexion.update("sucursal.editar-sucursal", sucursal);
               
                if (filasSucursal > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal editada correctamente");
                } else {
                    respuesta.setMensaje("No se pudo actualizar la información de la sucursal.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setMensaje("Error al editar la sucursal.");
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
                if (!existeSucursal(conexion, idSucursal)) {
                    respuesta.setMensaje("La sucursal que intenta bajar  no existe.");
                    return respuesta;
                }

                int filasAfectadas = conexion.update("sucursal.baja-sucursal", idSucursal);
                
                if (filasAfectadas > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal dada de baja correctamente");
                } else {
                    respuesta.setMensaje("No se pudo dar de baja la sucursal.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setMensaje("Error al eliminar la sucursal.");
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
    
    private static boolean existeSucursal(SqlSession conexion, int idSucursal) {
        Sucursal s = conexion.selectOne("sucursal.buscar-sucursal-id", idSucursal);
        return s != null;
    }
}
