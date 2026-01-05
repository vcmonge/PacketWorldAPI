/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import dto.Respuesta;
import java.util.HashMap;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.TipoUnidad;
import pojo.Unidad;
import pojo.UnidadBaja;
import utilidades.Constantes;

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

                conexionBD.delete("unidad.desasignar-por-unidad", unidadBaja.getIdUnidad());

                int filasUnidad = conexionBD.update("unidad.actualizarEstatusBaja", unidadBaja.getIdUnidad());

                int filasHistorial = conexionBD.insert("unidad.registrarBaja", unidadBaja);

                if (filasUnidad > 0 && filasHistorial > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Unidad dada de baja y liberada correctamente.");
                } else {
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
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
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
    
    public static List<Unidad> obtenerDisponibles() {
        List<Unidad> unidades = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                unidades = conexionBD.selectList("unidad.obtener-disponibles");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return unidades;
    }
    
    public static Respuesta asignarConductor(int idUnidad, int idConductor) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int esConductor = conexionBD.selectOne("unidad.es-conductor-registrado", idConductor);
                if (esConductor == 0) {
                    respuesta.setError(true);
                    respuesta.setMensaje("El colaborador seleccionado no está registrado como conductor o no existe.");
                    conexionBD.close();
                    return respuesta;
                }

                String errorUnidad = validarUnidadParaAsignacion(conexionBD, idUnidad);
                if (errorUnidad != null) {
                    respuesta.setError(true);
                    respuesta.setMensaje(errorUnidad);
                    conexionBD.close();
                    return respuesta;
                }

                HashMap<String, Integer> parametros = new HashMap<>();
                parametros.put("idUnidad", idUnidad);
                parametros.put("idConductor", idConductor);

                int filas = conexionBD.insert("unidad.asignar-conductor", parametros);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Unidad asignada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo realizar la asignación.");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al asignar: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        return respuesta;
    }

    public static Respuesta desasignarConductor(int idConductor) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int filas = conexionBD.delete("unidad.desasignar-conductor", idConductor);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Vehículo desasignado correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("El conductor no tenía vehículo asignado.");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al desasignar vehículo.");
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        return respuesta;
    }
    
    public static Respuesta cambiarAsignacion(int idUnidadNueva, int idConductorNuevo) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int esConductor = conexionBD.selectOne("unidad.es-conductor-registrado", idConductorNuevo);
                if (esConductor == 0) {
                    respuesta.setError(true);
                    respuesta.setMensaje("El conductor seleccionado no existe o no tiene licencia.");
                    conexionBD.close();
                    return respuesta;
                }

                String estatusUnidad = conexionBD.selectOne("unidad.obtener-estatus", idUnidadNueva);
                if (estatusUnidad == null || !estatusUnidad.equalsIgnoreCase("activa")) {
                    respuesta.setError(true);
                    respuesta.setMensaje("La unidad no existe o no está activa.");
                    conexionBD.close();
                    return respuesta;
                }

                Integer idConductorActual = conexionBD.selectOne("unidad.obtener-conductor-asignado", idUnidadNueva);
                
                if (idConductorActual != null) {
                    if (idConductorActual == idConductorNuevo) {
                        respuesta.setError(false);
                        respuesta.setMensaje("El conductor ya tiene asignada esta unidad.");
                        conexionBD.close();
                        return respuesta;
                    } else {
                        conexionBD.delete("unidad.desasignar-conductor", idConductorActual);
                    }
                }

                conexionBD.delete("unidad.desasignar-conductor", idConductorNuevo);

                HashMap<String, Integer> parametros = new HashMap<>();
                parametros.put("idUnidad", idUnidadNueva);
                parametros.put("idConductor", idConductorNuevo);
                
                int filas = conexionBD.insert("unidad.asignar-conductor", parametros);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Asignación actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo completar la asignación.");
                }

            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error interno: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        return respuesta;
    }
    
    private static String validarUnidadParaAsignacion(SqlSession conexionBD, int idUnidad) {
        String estatus = conexionBD.selectOne("unidad.obtener-estatus", idUnidad);
        if (estatus == null) return "La unidad no existe.";
        if (!estatus.equalsIgnoreCase("activa")) return "La unidad no está disponible (Baja o Mantenimiento).";

        Integer conductorAsignado = conexionBD.selectOne("unidad.obtener-conductor-asignado", idUnidad);
        if (conductorAsignado != null) return "La unidad ya está asignada a otro conductor.";

        return null; 
    }
    
    public static List<Unidad> obtenerUnidadesAsignadas() {
        List<Unidad> unidades = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                unidades = conexionBD.selectList("unidad.obtener-unidades-asignadas");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return unidades;
    }
}
