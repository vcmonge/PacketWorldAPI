/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

import com.google.gson.Gson;
import dominio.ColaboradorImp;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pojo.Colaborador;

@Path("colaborador")
public class ColaboradorWS {

    @Path("obtener-todos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerColaboradores() {
        return ColaboradorImp.obtenerColaboradores();
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta registrarColaborador(String json) {
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(json, Colaborador.class);
        return ColaboradorImp.registrarColaborador(colaborador);
    }
    
    @Path("editar")
    @PUT 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta editarColaborador(String json) {
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(json, Colaborador.class);
        return ColaboradorImp.editarColaborador(colaborador);
    }
    
    @Path("eliminar/{idColaborador}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@PathParam("idColaborador") int idColaborador) {
        return ColaboradorImp.eliminarColaborador(idColaborador);
    }
    
    @Path("buscar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarColaboradores(
            @QueryParam("criterio") String criterio, 
            @QueryParam("valor") String valor) {
        
        List<Colaborador> resultado = null;
        
        if (criterio != null && valor != null) {
            switch (criterio.toLowerCase()) {
                case "nombre":
                    resultado = ColaboradorImp.buscarPorNombre(valor);
                    break;
                case "rol":
                    resultado = ColaboradorImp.buscarPorRol(valor);
                    break;
                case "nopersonal":
                    resultado = ColaboradorImp.buscarPorNoPersonal(valor);
                    break;
                default:
                    throw new BadRequestException("Criterio de búsqueda no válido");
            }
        } else {
             throw new BadRequestException("Faltan parámetros de búsqueda");
        }
        
        return resultado;
    }
    
    @Path("subir-fotografia/{idColaborador}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta subirFotografia(@PathParam("idColaborador") Integer idColaborador, byte[] fotografia) {
        if(idColaborador != null && idColaborador > 0 && fotografia.length > 0) {
            return ColaboradorImp.guardarFotografia(idColaborador, fotografia);
        }
        throw new BadRequestException();
    }
    
    @Path("obtener-fotografia/{idColaborador}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador obtenerFotografia(@PathParam("idColaborador") Integer idColaborador) {
        if(idColaborador != null && idColaborador > 0) {
            return ColaboradorImp.obtenerFotografia(idColaborador);
        }
        throw new BadRequestException();
    }
    
    @Path("obtener/{idColaborador}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Colaborador obtenerPorId(@PathParam("idColaborador") Integer idColaborador) {
        if (idColaborador != null && idColaborador > 0) {
            return ColaboradorImp.obtenerPorId(idColaborador);
        }
        throw new BadRequestException();
    }
    
    @Path("cambiar-password")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Respuesta cambiarPassword(
            @FormParam("idColaborador") Integer idColaborador,
            @FormParam("passwordActual") String passwordActual,
            @FormParam("passwordNueva") String passwordNueva) {
        
        if (idColaborador != null && idColaborador > 0 && 
            passwordActual != null && !passwordActual.isEmpty() &&
            passwordNueva != null && !passwordNueva.isEmpty()) {
            
            return ColaboradorImp.cambiarPassword(idColaborador, passwordActual, passwordNueva);
        }
        throw new BadRequestException();
    }
    
    @Path("obtener-conductores-disponibles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerConductores() {
        return ColaboradorImp.obtenerConductores();
    }
    
    @Path("obtener-conductores-todos") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerTodosLosConductores() {
        return ColaboradorImp.obtenerTodosLosConductores();
    }
    
    @Path("obtener-conductores-sucursal/{idSucursal}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerConductoresPorSucursal(@PathParam("idSucursal") int idSucursal) {
        if (idSucursal > 0) {
            return ColaboradorImp.obtenerConductoresPorSucursal(idSucursal);
        }
        throw new BadRequestException("El ID de sucursal no es válido.");
    }
}
