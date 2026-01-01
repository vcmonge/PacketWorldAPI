/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

/**
 *
 * @author HECTO
 */
import dominio.ClienteImp;
import dto.Respuesta;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Cliente;

@Path("cliente")
public class ClienteWS {

    @GET
    @Path("obtener-todos")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta obtenerClientes() {
        return ClienteImp.obtenerClientes();
    }

    @GET
    @Path("buscar/nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta buscarClientePorNombre(@PathParam("nombre") String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            return ClienteImp.buscarClientePorNombre(nombre);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        respuesta.setMensaje("El parámetro nombre es obligatorio");
        return respuesta;
    }

    @GET
    @Path("buscar/correo/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta buscarClientePorCorreo(@PathParam("correo") String correo) {
        if (correo != null && !correo.trim().isEmpty()) {
            return ClienteImp.buscarClientePorCorreo(correo);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        respuesta.setMensaje("El parámetro correo es obligatorio");
        return respuesta;
    }

    @GET
    @Path("buscar/telefono/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta buscarClientePorTelefono(@PathParam("telefono") String telefono) {
        if (telefono != null && !telefono.trim().isEmpty()) {
            return ClienteImp.buscarClientePorTelefono(telefono);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        respuesta.setMensaje("El parámetro teléfono es obligatorio");
        return respuesta;
    }

    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarCliente(Cliente cliente) {
        if (cliente.getNombre() != null && !cliente.getNombre().isEmpty() &&
            cliente.getApellidoPaterno() != null && !cliente.getApellidoPaterno().isEmpty() &&
            cliente.getTelefono() != null && !cliente.getTelefono().isEmpty() &&
            cliente.getCorreo() != null && !cliente.getCorreo().isEmpty() &&
            cliente.getCalle() != null && !cliente.getCalle().isEmpty() &&
            cliente.getIdColonia() != null && cliente.getIdColonia() > 0) {
            
            return ClienteImp.registrarCliente(cliente);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        respuesta.setMensaje("Todos los campos obligatorios del cliente y su dirección deben ser proporcionados");
        return respuesta;
    }

    @PUT
    @Path("editar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarCliente(Cliente cliente) {
        if (cliente.getIdCliente() != null && cliente.getIdCliente() > 0 &&
            cliente.getIdDireccion() != null && cliente.getIdDireccion() > 0 &&
            cliente.getNombre() != null && !cliente.getNombre().isEmpty() &&
            cliente.getApellidoPaterno() != null && !cliente.getApellidoPaterno().isEmpty() &&
            cliente.getTelefono() != null && !cliente.getTelefono().isEmpty() &&
            cliente.getCorreo() != null && !cliente.getCorreo().isEmpty() &&
            cliente.getCalle() != null && !cliente.getCalle().isEmpty() &&
            cliente.getIdColonia() != null && cliente.getIdColonia() > 0) {
            
            return ClienteImp.editarCliente(cliente);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        respuesta.setMensaje("Todos los campos obligatorios y los identificadores deben ser válidos para editar");
        return respuesta;
    }

    @DELETE
    @Path("eliminar/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminarCliente(@PathParam("idCliente") Integer idCliente) {
        if (idCliente != null && idCliente > 0) {
            return ClienteImp.eliminarCliente(idCliente);
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        respuesta.setMensaje("El ID del cliente es obligatorio para la eliminación");
        return respuesta;
    }
}