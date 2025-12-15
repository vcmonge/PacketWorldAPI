package ws;

import com.google.gson.Gson;
import dominio.SucursalImp;
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
import javax.ws.rs.core.Response;
import pojo.Sucursal;

@Path("sucursales")
public class SucursalWS {
    
   // En el método GET:
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSucursales() {
        // Cambio de tipo Respuesta a RSSucursales
        RSSucursales respuesta = SucursalImp.getAllSucursales(); 
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }   
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarSucursal(String json) {
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        
        // Validación básica de campos requeridos
        if (sucursal.getNombre() == null || sucursal.getCalle() == null || sucursal.getCp() == null) {
             return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta(true, "Datos obligatorios faltantes.", null))
                    .build();
        }

        Respuesta respuesta = SucursalImp.registrarSucursal(sucursal);
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }
    
    @PUT
    @Path("editar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarSucursal(String json) {
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        
        if (sucursal.getIdSucursal() == null || sucursal.getIdSucursal() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta(true, "ID de sucursal inválido.", null))
                    .build();
        }

        Respuesta respuesta = SucursalImp.editarSucursal(sucursal);
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }
    
    @DELETE
    @Path("eliminar/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarSucursal(@PathParam("idSucursal") Integer idSucursal) {
        if (idSucursal == null || idSucursal <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta(true, "ID de sucursal inválido.", null))
                    .build();
        }
        
        Respuesta respuesta = SucursalImp.eliminarSucursal(idSucursal);
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }
}