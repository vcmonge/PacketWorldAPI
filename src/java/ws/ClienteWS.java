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

    public ClienteWS() {
    }

    @Path("obtener-todos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerTodos() {
        return ClienteImp.obtenerTodos();
    }

    // Búsqueda por nombre, teléfono o correo según PDF
    @Path("buscar/{parametro}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscar(@PathParam("parametro") String parametro) {
        if (Validaciones.esVacio(parametro)) {
            throw new BadRequestException("El parámetro de búsqueda es obligatorio.");
        }
        return ClienteImp.buscar(parametro);
    }

    @Path("registrar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(String json) {
        if (Validaciones.esVacio(json)) {
            throw new BadRequestException("El cuerpo de la solicitud no puede estar vacío.");
        }
        
        Gson gson = new Gson();
        try {
            Cliente cliente = gson.fromJson(json, Cliente.class);
            
            // Validaciones de campos obligatorios según base de datos
            if (Validaciones.esVacio(cliente.getNombre()) || 
                Validaciones.esVacio(cliente.getApellidoPaterno()) || 
                Validaciones.esVacio(cliente.getTelefono()) || 
                Validaciones.esVacio(cliente.getCorreo())) {
                throw new BadRequestException("Faltan datos obligatorios para el registro.");
            }
            
            return ClienteImp.registrar(cliente);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Path("editar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editar(String json) {
        if (Validaciones.esVacio(json)) {
            throw new BadRequestException("El cuerpo de la solicitud no puede estar vacío.");
        }
        
        Gson gson = new Gson();
        try {
            Cliente cliente = gson.fromJson(json, Cliente.class);
            
            if (cliente.getIdCliente() == null || cliente.getIdCliente() <= 0) {
                throw new BadRequestException("El ID del cliente es necesario para la edición.");
            }
            
            return ClienteImp.editar(cliente);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Path("eliminar/{idCliente}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@PathParam("idCliente") String idCliente) {
        if (Validaciones.esVacio(idCliente)) {
            throw new BadRequestException("El ID del cliente es obligatorio.");
        }
        if (!Validaciones.esNumerico(idCliente)) {
            throw new BadRequestException("El ID debe ser numérico.");
        }
        
        int id = Integer.parseInt(idCliente);
        return ClienteImp.eliminar(id);
    }
}

