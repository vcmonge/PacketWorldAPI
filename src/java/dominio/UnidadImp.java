/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.TipoUnidad;
import pojo.Unidad;
import pojo.UnidadBaja;

public class UnidadImp {

    // 1. OBTENER TODAS LAS UNIDADES (Para la tabla principal)
    public static List<Unidad> obtenerUnidades() {
        List<Unidad> unidades = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                // Llama al SELECT con JOIN que definimos en el XML
                unidades = conexionBD.selectList("unidad.obtenerTodas");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return unidades;
    }

    // 2. OBTENER CATALOGO DE TIPOS (Para llenar el ComboBox en el formulario)
    public static List<TipoUnidad> obtenerTiposUnidad() {
        List<TipoUnidad> tipos = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                tipos = conexionBD.selectList("unidad.obtenerTipos");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return tipos;
    }

    // 3. REGISTRAR UNIDAD
    public static Respuesta registrarUnidad(Unidad unidad) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // No mandamos NII (se genera solo) ni Estatus (hardcodeado a 'activa' en el XML)
                int filas = conexionBD.insert("unidad.registrar", unidad);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Unidad registrada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo registrar la unidad.");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                
                // Manejo de VIN Duplicado
                String mensajeError = "Error al registrar la unidad.";
                if (e.getCause() != null && e.getCause().getMessage() != null) {
                    String errorBD = e.getCause().getMessage().toLowerCase();
                    if (errorBD.contains("duplicate entry") && errorBD.contains("vin")) {
                        mensajeError = "El VIN ingresado ya pertenece a otra unidad.";
                    }
                }
                
                respuesta.setError(true);
                respuesta.setMensaje(mensajeError);
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }

    // 4. EDITAR UNIDAD
    public static Respuesta editarUnidad(Unidad unidad) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // NO se debe permitir editar el VIN ni el NII por reglas de negocio
                int filas = conexionBD.update("unidad.editar", unidad);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Unidad actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró la unidad para editar.");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al editar la información.");
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }

    // 5. DAR DE BAJA 
    public static Respuesta darBajaUnidad(UnidadBaja unidadBaja) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // PASO 1: Cambiar estatus en tabla 'unidad' a 'baja'
                int filasUnidad = conexionBD.update("unidad.actualizarEstatusBaja", unidadBaja.getIdUnidad());

                // PASO 2: Insertar el motivo en tabla 'unidad_baja'
                int filasHistorial = conexionBD.insert("unidad.registrarBaja", unidadBaja);

                // Si ambos pasos funcionan, hacemos commit.
                if (filasUnidad > 0 && filasHistorial > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Unidad dada de baja correctamente.");
                } else {
                    // Si algo falló (ej. la unidad no existía), hacemos rollback
                    conexionBD.rollback();
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo completar la baja de la unidad.");
                }

            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al procesar la baja: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }
    
    public static List<Unidad> buscarUnidad(String textoBusqueda) {
        List<Unidad> unidades = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                unidades = conexionBD.selectList("unidad.buscar", textoBusqueda);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return unidades;
    }
}
