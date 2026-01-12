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

    public static List<Cliente> obtenerClientes() {
        List<Cliente> clientes = null;
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                clientes = conexion.selectList("cliente.obtener-clientes");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return clientes;
    }

    public static List<Cliente> buscarClientePorNombre(String nombre) {
        List<Cliente> clientes = null;
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                clientes = conexion.selectList("cliente.buscar-cliente-nombre", nombre);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return clientes;
    }

    public static List<Cliente> buscarClientePorCorreo(String correo) {
        List<Cliente> clientes = null;
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                clientes = conexion.selectList("cliente.buscar-cliente-correo", correo);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return clientes;
    }

    public static List<Cliente> buscarClientePorTelefono(String telefono) {
        List<Cliente> clientes = null;
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                clientes = conexion.selectList("cliente.buscar-cliente-telefono", telefono);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return clientes;
    }

    public static Respuesta registrarCliente(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                
                int existeCorreo = conexion.selectOne("cliente.verificar-correo", cliente.getCorreo());
                if (existeCorreo > 0) {
                    respuesta.setMensaje("El correo electrónico " + cliente.getCorreo() + " ya está registrado.");
                    return respuesta;
                }

                int existeTelefono = conexion.selectOne("cliente.verificar-telefono", cliente.getTelefono());
                if (existeTelefono > 0) {
                    respuesta.setMensaje("El número de teléfono " + cliente.getTelefono() + " ya está registrado.");
                    return respuesta;
                }

                int filasAfectadas = conexion.insert("cliente.registrar-cliente", cliente);
                
                if (filasAfectadas > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente registrado correctamente.");
                } else {
                    respuesta.setMensaje("No se pudo registrar la información del cliente.");
                }
            } catch (Exception e) {
                respuesta.setMensaje("Error interno del servidor: " + e.getMessage());
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(utilidades.Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }

    public static Respuesta editarCliente(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                if (!existeCliente(conexion, cliente.getIdCliente())) {
                    respuesta.setMensaje("El cliente que intenta editar no existe.");
                    return respuesta;
                }

                int filasCliente = conexion.update("cliente.editar-cliente", cliente);
               
                if (filasCliente > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente editado correctamente.");
                } else {
                    respuesta.setMensaje("No se pudo actualizar la información del cliente.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setMensaje("Error interno al editar el cliente.");
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }
        
    public static Cliente buscarClientePorId(int idCliente) {
        Cliente cliente = null;
        SqlSession conexion = MyBatisUtil.getSession();
        if (conexion != null) {
            try {
                cliente = conexion.selectOne("cliente.buscar-cliente-id", idCliente);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        }
        return cliente;
    }
    
    public static Respuesta eliminarCliente(Integer idCliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                if (!existeCliente(conexion, idCliente)) {
                    respuesta.setMensaje("El cliente que intenta eliminar no existe.");
                    return respuesta;
                }

                int filasAfectadas = conexion.delete("cliente.eliminar-cliente", idCliente);
                
                if (filasAfectadas > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente eliminado correctamente.");
                } else {
                    respuesta.setMensaje("No se pudo completar la eliminación del cliente.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                respuesta.setMensaje("Error al procesar la eliminación. Es posible que tenga registros asociados.");
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(utilidades.Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }

    private static boolean existeCliente(SqlSession conexion, int idCliente) {
        pojo.Cliente cliente = conexion.selectOne("cliente.buscar-cliente-id", idCliente);
        return cliente != null;
    }
}