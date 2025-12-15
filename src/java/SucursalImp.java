package dominio;

import dto.RSSucursales;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Sucursal;

public class SucursalImp {
    
    public static RSSucursales getAllSucursales() {
        RSSucursales respuesta = new RSSucursales();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                List<Sucursal> sucursales = conexion.selectList("modelo.mybatis.mapper.SucursalMapper.getAllSucursales");
                if (sucursales != null && !sucursales.isEmpty()) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursales obtenidas correctamente.");
                    respuesta.setSucursales(sucursales);
                } else {
                    respuesta.setError(false);
                    respuesta.setMensaje("No existen sucursales registradas.");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al obtener las sucursales: " + e.getMessage());
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
    
    public static Respuesta registrarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasAfectadas = conexion.insert("modelo.mybatis.mapper.SucursalMapper.registrarSucursal", sucursal);
                conexion.commit();
                if (filasAfectadas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal registrada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo registrar la sucursal.");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al registrar la sucursal: " + e.getMessage());
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
    
    public static Respuesta editarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasAfectadas = conexion.update("modelo.mybatis.mapper.SucursalMapper.editarSucursal", sucursal);
                conexion.commit();
                if (filasAfectadas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo actualizar la sucursal.");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al actualizar la sucursal: " + e.getMessage());
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
    
    public static Respuesta eliminarSucursal(Integer idSucursal) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasAfectadas = conexion.update("modelo.mybatis.mapper.SucursalMapper.eliminarSucursal", idSucursal);
                conexion.commit();
                if (filasAfectadas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Sucursal dada de baja correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo dar de baja la sucursal.");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al eliminar la sucursal: " + e.getMessage());
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
}