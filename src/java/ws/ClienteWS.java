/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

/**
 *
 * @author HECTO
 */
import com.google.gson.Gson;
import dominio.ClienteImp;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
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
import utilidades.Validaciones;

@Path("cliente")
public class ClienteWS {

    @GET
    @Path("obtener-todos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerClientes() {
        return ClienteImp.obtenerClientes();
    }

    @GET
    @Path("buscar/nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarClientePorNombre(@PathParam("nombre") String nombre) {
        if (Validaciones.esVacio(nombre)) {
            throw new BadRequestException("El parámetro nombre es obligatorio");
        }
        return ClienteImp.buscarClientePorNombre(nombre);
    }

    @GET
    @Path("buscar/correo/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarClientePorCorreo(@PathParam("correo") String correo) {
        if (Validaciones.esVacio(correo)) {
            throw new BadRequestException("El parámetro correo es obligatorio");
        }
        return ClienteImp.buscarClientePorCorreo(correo);
    }

    @GET
    @Path("buscar/telefono/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarClientePorTelefono(@PathParam("telefono") String telefono) {
        if (Validaciones.esVacio(telefono)) {
            throw new BadRequestException("El parámetro teléfono es obligatorio");
        }
        return ClienteImp.buscarClientePorTelefono(telefono);
    }

    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarCliente(String json) {
        Gson gson = new Gson();
        try {
            Cliente cliente = gson.fromJson(json, Cliente.class);
            if (Validaciones.esVacio(cliente.getNombre()) || 
                Validaciones.esVacio(cliente.getApellidoPaterno()) ||
                Validaciones.esVacio(cliente.getTelefono()) || 
                Validaciones.esVacio(cliente.getCorreo()) ||
                Validaciones.esVacio(cliente.getCalle()) || 
                (cliente.getIdColonia() == null || cliente.getIdColonia() <= 0)) {
                throw new BadRequestException("Todos los campos obligatorios deben ser proporcionados");
            }
            return ClienteImp.registrarCliente(cliente);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PUT
    @Path("editar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarCliente(String json) {
        Gson gson = new Gson();
        try {
            Cliente cliente = gson.fromJson(json, Cliente.class);
            if (cliente.getIdCliente() == null || cliente.getIdCliente() <= 0) {
                throw new BadRequestException("El ID del cliente es obligatorio para editar");
            }
            return ClienteImp.editarCliente(cliente);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @DELETE
    @Path("eliminar/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminarCliente(@PathParam("idCliente") String idClienteStr) {
        if (Validaciones.esVacio(idClienteStr) || !Validaciones.esNumerico(idClienteStr)) {
            throw new BadRequestException("El ID del cliente debe ser numérico y obligatorio");
        }
        int idCliente = Integer.parseInt(idClienteStr);
        return ClienteImp.eliminarCliente(idCliente);
    }
}