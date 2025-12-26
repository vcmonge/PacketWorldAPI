/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author HECTO
 */
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import utilidades.Constantes;

public class ClienteImp {

    public static List<Cliente> obtenerTodos() {
        List<Cliente> clientes = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // Llama al select 'obtenerTodos' definido en ClienteMapper.xml
                clientes = conexionBD.selectList("cliente.obtenerTodos");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return clientes;
    }

    public static List<Cliente> buscar(String parametro) {
        List<Cliente> clientes = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // Llama al select 'buscar' que filtra por nombre, teléfono o correo
                clientes = conexionBD.selectList("cliente.buscar", parametro);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return clientes;
    }

    public static Respuesta registrar(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // 1. Insertar la dirección primero para obtener su ID
                int filasDireccion = conexionBD.insert("cliente.registrarDireccion", cliente);
                
                // MyBatis actualiza el objeto 'cliente' con el idDireccion generado (keyProperty)
                if (filasDireccion > 0) {
                    // 2. Insertar el cliente usando el idDireccion obtenido
                    int filasCliente = conexionBD.insert("cliente.registrar", cliente);

                    if (filasCliente > 0) {
                        conexionBD.commit();
                        respuesta.setError(false);
                        respuesta.setMensaje("Cliente registrado exitosamente.");
                    } else {
                        respuesta.setMensaje("No se pudo registrar la información del cliente.");
                    }
                } else {
                    respuesta.setMensaje("No se pudo registrar la dirección del cliente.");
                }
            } catch (Exception e) {
                respuesta.setMensaje("Error al registrar cliente: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }

        return respuesta;
    }

    public static Respuesta editar(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                // Actualizar datos de cliente y dirección en la misma transacción
                int filasCliente = conexionBD.update("cliente.editar", cliente);
                int filasDireccion = conexionBD.update("cliente.editarDireccion", cliente);

                if (filasCliente > 0 && filasDireccion > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Información del cliente actualizada exitosamente.");
                } else {
                    respuesta.setMensaje("No se pudo actualizar la información completa del cliente.");
                }
            } catch (Exception e) {
                respuesta.setMensaje("Error al editar cliente: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }

        return respuesta;
    }

    public static Respuesta eliminar(int idCliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.delete("cliente.eliminar", idCliente);
                
                if (filasAfectadas > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente eliminado exitosamente.");
                } else {
                    respuesta.setMensaje("No se pudo eliminar el cliente.");
                }
            } catch (Exception e) {
                respuesta.setMensaje("Error al eliminar cliente: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }

        return respuesta;
    }
}