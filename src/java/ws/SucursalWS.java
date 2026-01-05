/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

import com.google.gson.Gson;
import dominio.SucursalImp;
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
import utilidades.Validaciones;

/**
 *
 * @author HECTO
 */
@Path("sucursal")
public class SucursalWS {

    @GET
    @Path("obtener-todos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursales() {
        return SucursalImp.obtenerSucursales();
    }
    
    @GET
    @Path("obtener-activas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursalesActivas() {
        return SucursalImp.obtenerSucursalesActivas();
    }

    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarSucursal(String json) {
        Gson gson = new Gson();
        try {
            Sucursal sucursal = gson.fromJson(json, Sucursal.class);
            if (Validaciones.esVacio(sucursal.getNombre()) || 
                Validaciones.esVacio(sucursal.getCalle()) || 
                (sucursal.getIdColonia() == null || sucursal.getIdColonia() <= 0)) {
                throw new BadRequestException("Todos los campos obligatorios (nombre, calle, colonia) deben ser proporcionados");
            }
            if (Validaciones.esVacio(sucursal.getEstatus())) {
                sucursal.setEstatus("activa");
            }
            return SucursalImp.registrarSucursal(sucursal);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PUT
    @Path("editar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarSucursal(String json) {
        Gson gson = new Gson();
        try {
            Sucursal sucursal = gson.fromJson(json, Sucursal.class);
            if (sucursal.getIdSucursal() == null || sucursal.getIdSucursal() <= 0) {
                throw new BadRequestException("El ID de la sucursal es obligatorio para editar");
            }
            return SucursalImp.editarSucursal(sucursal);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PUT
    @Path("bajar/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminarSucursal(@PathParam("idSucursal") String idSucursalStr) {
        if (Validaciones.esVacio(idSucursalStr) || !Validaciones.esNumerico(idSucursalStr)) {
            throw new BadRequestException("El ID de la sucursal debe ser numérico y obligatorio");
        }
        int idSucursal = Integer.parseInt(idSucursalStr);
        return SucursalImp.bajarSucursal(idSucursal);
    }
    
    @GET
    @Path("buscar/id/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sucursal buscarSucursalPorId(@PathParam("idSucursal") int idSucursal) {
        if (idSucursal <= 0) {
            throw new BadRequestException("El parámetro idSucursal debe ser un número entero mayor que cero");
        }
        return SucursalImp.buscarSucursalPorId(idSucursal);
    }
}