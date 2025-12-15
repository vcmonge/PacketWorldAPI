/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;

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
                respuesta.setMensaje(manejarErrorBD(e)); // Extraje la lógica de errores a un método privado abajo
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
                // 1. Update Colaborador
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
}
