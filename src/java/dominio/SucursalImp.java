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
    
    public static List<Sucursal> obtenerSucursalesActivas() {
        List<Sucursal> sucursales = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursales = conexionBD.selectList("sucursal.obtenerActivas");
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
                //Insertamos dirección (se llenará el idDireccion en el objeto sucursal)
                int filasDireccion = conexionBD.insert("sucursal.registrarDireccion", sucursal);
                
                if (filasDireccion > 0 && sucursal.getIdDireccion() != null) {
                    //Insertamos sucursal usando el idDireccion generado
                    int filasSucursal = conexionBD.insert("sucursal.registrar", sucursal);
                    
                    if (filasSucursal > 0) {
                        conexionBD.commit();
                        respuesta.setError(false);
                        respuesta.setMensaje("Sucursal registrada correctamente.");
                        respuesta.setSucursal(sucursal);
                    } else {
                        respuesta.setError(true);
                        respuesta.setMensaje("Error al registrar los datos de la sucursal.");
                    }
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("Error al registrar la dirección.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error en la base de datos: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }
    
    public static Respuesta editarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                // Actualizamos ambas tablas
                int filasSucursal = conexionBD.update("sucursal.editar", sucursal);
                int filasDireccion = conexionBD.update("sucursal.editarDireccion", sucursal);
                
                if (filasSucursal > 0 || filasDireccion > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo actualizar la información.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al editar: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }
    
    public static Respuesta eliminarSucursal(int idSucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int filas = conexionBD.update("sucursal.eliminar", idSucursal);
                if (filas > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal dada de baja correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró la sucursal para dar de baja.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al eliminar: " + e.getMessage());
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
