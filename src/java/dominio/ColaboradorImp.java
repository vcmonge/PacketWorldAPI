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
import pojo.Colaborador;
import utilidades.Constantes;

public class ColaboradorImp {

    public static List<Colaborador> obtenerColaboradores() {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.obtener-todos");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }
    
    public static Respuesta registrarColaborador(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                if (colaborador.getNumeroLicencia() != null && !colaborador.getNumeroLicencia().trim().isEmpty()) {
                    if (existeLicencia(conexionBD, colaborador.getNumeroLicencia(), 0)) { 
                        respuesta.setError(true);
                        respuesta.setMensaje("El número de licencia ya se encuentra registrado.");
                        conexionBD.close();
                        return respuesta;
                    }
                }
                // 1. Insertar Colaborador (Padre)
                int filas = conexionBD.insert("colaborador.registrar", colaborador);

                // 2. Insertar Conductor (Hijo)
                // Si trae licencia, asumimos que es conductor y guardamos en la tabla hija.
                if (colaborador.getNumeroLicencia() != null && !colaborador.getNumeroLicencia().trim().isEmpty()) {
                    conexionBD.insert("colaborador.registrarConductor", colaborador);
                }

                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Registro exitoso");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo registrar al colaborador");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje(manejarErrorBD(e)); 
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la base de datos");
        }
        return respuesta;
    }
    
    public static Respuesta editarColaborador(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                if (colaborador.getNumeroLicencia() != null && !colaborador.getNumeroLicencia().trim().isEmpty()) {
                    if (existeLicencia(conexionBD, colaborador.getNumeroLicencia(), colaborador.getIdColaborador())) {
                        respuesta.setError(true);
                        respuesta.setMensaje("El número de licencia ya pertenece a otro conductor.");
                        conexionBD.close();
                        return respuesta;
                    }
                }
                int filas = conexionBD.update("colaborador.editar", colaborador);

                // 2. Update Conductor (Si trae licencia)
                if (colaborador.getNumeroLicencia() != null && !colaborador.getNumeroLicencia().trim().isEmpty()) {
                    conexionBD.update("colaborador.editarConductor", colaborador);
                }

                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Información actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró el colaborador para editar.");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje(manejarErrorBD(e));
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }
    
    private static boolean existeLicencia(SqlSession conexionBD, String licencia, int idColaborador) {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("numeroLicencia", licencia);
        parametros.put("idConductor", idColaborador);
        
        int coincidencias = conexionBD.selectOne("colaborador.verificar-licencia-duplicada", parametros);
        return coincidencias > 0;
    }
    
    public static Respuesta eliminarColaborador(int idColaborador) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // 1. Delete Conductor
                conexionBD.delete("colaborador.eliminarConductor", idColaborador);
                // 2. Delete Colaborador
                int filas = conexionBD.delete("colaborador.eliminar", idColaborador);
                
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Colaborador eliminado correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("El colaborador no existe.");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("No se puede eliminar porque tiene historial activo (envíos).");
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Sin conexión a la base de datos.");
        }
        return respuesta;
    }

    // Método auxiliar para limpiar el código principal
    private static String manejarErrorBD(Exception e) {
        String mensaje = "Error interno en la base de datos.";
        if (e.getCause() != null && e.getCause().getMessage() != null) {
            String causa = e.getCause().getMessage().toLowerCase();
            if (causa.contains("duplicate entry")) {
                if (causa.contains("curp")) return "La CURP ya está registrada.";
                if (causa.contains("correo")) return "El correo ya está registrado.";
                if (causa.contains("nopersonal")) return "El número de personal ya existe.";
                if (causa.contains("licencia")) return "La licencia ya está registrada.";
            }
        }
        return mensaje;
    }
    
    // Opción 1: Buscar por Nombre
    public static List<Colaborador> buscarPorNombre(String nombre) {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.buscarPorNombre", nombre);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }

    // Opción 2: Buscar por Rol
    public static List<Colaborador> buscarPorRol(String rol) {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.buscarPorRol", rol);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }

    // Opción 3: Buscar por No. Personal
    public static List<Colaborador> buscarPorNoPersonal(String noPersonal) {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.buscarPorNoPersonal", noPersonal);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }
    
    public static Respuesta guardarFotografia(int idColaborador, byte[] fotografia) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                Colaborador colaborador = new Colaborador();
                colaborador.setIdColaborador(idColaborador);
                colaborador.setFotografia(fotografia);
                int filasAfectadas = conexionBD.update("colaborador.guardar-fotografia", colaborador);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("La fotografía del colaborador ha sido guardado correctamente");
                } else {
                    respuesta.setMensaje("La fotografía del profesor no ha sido guardada, inténtelo más tarde");
                }
            } catch (Exception e) {
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }
    
    public static Colaborador obtenerFotografia(int idColaborador) {
        Colaborador colaborador = new Colaborador();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaborador = conexionBD.selectOne("colaborador.obtener-fotografia", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaborador;
    }
    
    public static Colaborador obtenerPorId(int idColaborador) {
        Colaborador colaborador = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaborador = conexionBD.selectOne("colaborador.obtenerPorId", idColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaborador;
    }
    
    public static Respuesta cambiarPassword(int idColaborador, String passwordActual, String passwordNueva) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("idColaborador", idColaborador);
                parametros.put("passwordActual", passwordActual);
                parametros.put("passwordNueva", passwordNueva);

                int filas = conexionBD.update("colaborador.cambiarPassword", parametros);
                conexionBD.commit();

                if (filas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Contraseña actualizada correctamente.");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("La contraseña actual es incorrecta.");
                }
            } catch (Exception e) {
                if (conexionBD != null) conexionBD.rollback();
                e.printStackTrace();
                respuesta.setError(true);
                respuesta.setMensaje("Error al cambiar la contraseña.");
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        return respuesta;
    }
    
    public static List<Colaborador> obtenerConductores() {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("obtener-conductores-disponibles");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }
    
    public static List<Colaborador> obtenerTodosLosConductores() {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.obtener-todos-conductores");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return colaboradores;
    }
}
