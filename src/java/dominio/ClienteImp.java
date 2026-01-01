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

public class ClienteImp {

    public static Respuesta obtenerClientes() {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                List<Cliente> clientes = conexion.selectList("cliente.obtener-clientes");
                respuesta.setError(false);
                respuesta.setMensaje("Clientes obtenidos correctamente");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión a la base de datos");
        }
        
        return respuesta;
    }

    public static Respuesta buscarClientePorNombre(String nombre) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                List<Cliente> clientes = conexion.selectList("cliente.buscar-cliente-nombre", nombre);
                respuesta.setError(false);
                respuesta.setMensaje("Búsqueda por nombre realizada correctamente");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión a la base de datos");
        }
        
        return respuesta;
    }

    public static Respuesta buscarClientePorCorreo(String correo) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                List<Cliente> clientes = conexion.selectList("cliente.buscar-cliente-correo", correo);
                respuesta.setError(false);
                respuesta.setMensaje("Búsqueda por correo realizada correctamente");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión a la base de datos");
        }
        
        return respuesta;
    }

    public static Respuesta buscarClientePorTelefono(String telefono) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                List<Cliente> clientes = conexion.selectList("cliente.buscar-cliente-telefono", telefono);
                respuesta.setError(false);
                respuesta.setMensaje("Búsqueda por teléfono realizada correctamente");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión a la base de datos");
        }
        
        return respuesta;
    }

    public static Respuesta registrarCliente(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
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
                        conexion.rollback();
                        respuesta.setError(true);
                        respuesta.setMensaje("Error al registrar la información del cliente");
                    }
                } else {
                    conexion.rollback();
                    respuesta.setError(true);
                    respuesta.setMensaje("Error al registrar la dirección del cliente");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión a la base de datos");
        }
        
        return respuesta;
    }

    public static Respuesta editarCliente(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasCliente = conexion.update("cliente.editar-cliente", cliente);
                int filasDireccion = conexion.update("cliente.editar-direccion", cliente);
                
                if (filasCliente > 0 && filasDireccion > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente editado correctamente");
                } else {
                    conexion.rollback();
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo actualizar la información completa del cliente");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión a la base de datos");
        }
        
        return respuesta;
    }

    public static Respuesta eliminarCliente(Integer idCliente) {
        Respuesta respuesta = new Respuesta();
        SqlSession conexion = MyBatisUtil.getSession();
        
        if (conexion != null) {
            try {
                int filasAfectadas = conexion.delete("cliente.eliminar-cliente", idCliente);
                
                if (filasAfectadas > 0) {
                    conexion.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Cliente eliminado correctamente");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró el cliente con el ID proporcionado");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexion.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión a la base de datos");
        }
        
        return respuesta;
    }
}