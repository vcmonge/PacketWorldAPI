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
                int filasDireccion = conexion.insert("cliente.registrar-direccion", cliente);
                
                if (filasDireccion > 0 && cliente.getIdDireccion() != null) {
                    int filasCliente = conexion.insert("cliente.registrar-cliente", cliente);
                    
                    if (filasCliente > 0) {
                        conexion.commit();
                        respuesta.setError(false);
                        respuesta.setMensaje("Cliente registrado correctamente");
                    } else {
                        respuesta.setMensaje("Error al registrar la información del cliente");
                    }
                } else {
                    respuesta.setMensaje("Error al registrar la dirección del cliente");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }

    public static Respuesta editarCliente(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasCliente = conexion.update("cliente.editar-cliente", cliente);
                int filasDireccion = conexion.update("cliente.editar-direccion", cliente);
               
                if (filasCliente > 0 || filasDireccion > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente editado correctamente");
                } else {
                    respuesta.setMensaje("No se pudo actualizar la información del cliente. Verifique los IDs.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }

    public static Respuesta eliminarCliente(Integer idCliente) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasAfectadas = conexion.delete("cliente.eliminar-cliente", idCliente);
                
                if (filasAfectadas > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente eliminado correctamente");
                } else {
                    respuesta.setMensaje("No se encontró el cliente con el ID proporcionado");
                }
            } catch (Exception e) {
                e.printStackTrace();
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
}