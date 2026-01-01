/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

/**
 *
 * @author HECTO
 */
import dominio.SucursalImp;
import dto.RSSucursal;
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
import pojo.Sucursal;

@Path("sucursal")
public class SucursalWS {
    
    @Path("obtener-todos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursales() {
        return SucursalImp.obtenerSucursales();
    }
    
    @Path("obtener-activas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursalesActivas() {
        return SucursalImp.obtenerSucursalesActivas();
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RSSucursal registrarSucursal(Sucursal sucursal) {
        if (sucursal != null && sucursal.getNombre() != null && !sucursal.getNombre().isEmpty() 
            && sucursal.getCalle() != null && sucursal.getIdColonia() != null) {
            return SucursalImp.registrarSucursal(sucursal);
        }
        throw new BadRequestException();
    }
    
    @Path("editar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta editarSucursal(Sucursal sucursal) {
        if (sucursal != null && sucursal.getIdSucursal() != null && sucursal.getIdSucursal() > 0 
            && sucursal.getIdDireccion() != null) {
            return SucursalImp.editarSucursal(sucursal);
        }
        throw new BadRequestException();
    }
    
    @Path("eliminar/{idSucursal}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminarSucursal(@PathParam("idSucursal") Integer idSucursal) {
        if (idSucursal != null && idSucursal > 0) {
            return SucursalImp.eliminarSucursal(idSucursal);
        }
        throw new BadRequestException();
    }
}