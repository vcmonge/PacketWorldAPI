/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author HECTO
 */
import dto.RSSucursal;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Sucursal;

public class SucursalImp {
    
    public static List<Sucursal> obtenerSucursales() {
        List<Sucursal> sucursales = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursales = conexionBD.selectList("sucursal.obtenerTodos");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return sucursales;
    }
    
    public static RSSucursal registrarSucursal(Sucursal sucursal) {
        RSSucursal respuesta = new RSSucursal();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                // 1. Insertar en tabla DIRECCION usando el objeto Sucursal.
                // MyBatis llenará sucursal.idDireccion automáticamente gracias a useGeneratedKeys.
                int filasDireccion = conexionBD.insert("sucursal.registrarDireccion", sucursal);
                
                // 2. Validar que se generó el ID de dirección
                if (filasDireccion > 0 && sucursal.getIdDireccion() != null) {
                    
                    // 3. Insertar en tabla SUCURSAL usando el mismo objeto (que ya tiene el idDireccion)
                    int filasSucursal = conexionBD.insert("sucursal.registrar", sucursal);
                    
                    if (filasSucursal > 0) {
                        conexionBD.commit();
                        respuesta.setError(false);
                        respuesta.setMensaje("Sucursal registrada correctamente.");
                        respuesta.setSucursal(sucursal);
                    } else {
                        respuesta.setError(true);
                        respuesta.setMensaje("Error al registrar la información de la sucursal.");
                    }
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("Error al registrar la dirección de la sucursal.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error en la base de datos al guardar: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
    
    public static Respuesta editarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                // 1. Actualizar tabla SUCURSAL (solo nombre)
                int filasSucursal = conexionBD.update("sucursal.editar", sucursal);
                
                // 2. Actualizar tabla DIRECCION (calle, numero, colonia) usando el mismo objeto Sucursal
                int filasDireccion = conexionBD.update("sucursal.editarDireccion", sucursal);
                
                if (filasSucursal > 0 || filasDireccion > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Información actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo actualizar la información.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al editar la sucursal: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
    
    public static Respuesta eliminarSucursal(int idSucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("sucursal.eliminar", idSucursal);
                
                if (filasAfectadas > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("La sucursal ha sido dada de baja correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo dar de baja la sucursal.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al eliminar la sucursal: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
}
