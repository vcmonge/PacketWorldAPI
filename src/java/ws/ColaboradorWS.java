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
    
    // REGISTRO GENÉRICO (Admin)
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta registrarColaborador(String json) {
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(json, Colaborador.class);
        return ColaboradorImp.registrarColaborador(colaborador);
    }

    // REGISTRO ESPECÍFICO (Para validar licencia obligatoria)
    @Path("registrar-conductor")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta registrarConductor(String json) {
        Gson gson = new Gson();
        Colaborador conductor = gson.fromJson(json, Colaborador.class);
        
        if(conductor.getNumeroLicencia() == null || conductor.getNumeroLicencia().isEmpty()) {
            return new Respuesta(true, "La licencia es obligatoria para registrar un conductor.");
        }
        return ColaboradorImp.registrarColaborador(conductor);
    }
    
    // EDICIÓN GENÉRICA (Admin - Desktop)
    @Path("editar")
    @PUT 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta editarColaborador(String json) {
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(json, Colaborador.class);
        return ColaboradorImp.editarColaborador(colaborador);
    }

    // EDICIÓN ESPECÍFICA (Móvil - Conductor)
    @Path("editar-conductor")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta editarConductorMovil(String json) {
        Gson gson = new Gson();
        Colaborador conductor = gson.fromJson(json, Colaborador.class);

        if (conductor.getNumeroLicencia() == null || conductor.getNumeroLicencia().isEmpty()) {
            return new Respuesta(true, "El número de licencia es obligatorio.");
        }
        return ColaboradorImp.editarColaborador(conductor);
    }
    
    @Path("eliminar/{idColaborador}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@PathParam("idColaborador") int idColaborador) {
        return ColaboradorImp.eliminarColaborador(idColaborador);
    }

    // URL: .../ws/colaborador/buscar-nombre/Juan%20Perez
    @Path("buscar-nombre/{nombre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarPorNombre(@PathParam("nombre") String nombre) {
        return ColaboradorImp.buscarPorNombre(nombre);
    }

    // URL: .../ws/colaborador/buscar-rol/Conductor
    @Path("buscar-rol/{rol}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarPorRol(@PathParam("rol") String rol) {
        return ColaboradorImp.buscarPorRol(rol);
    }

    // URL: .../ws/colaborador/buscar-nopersonal/C001
    @Path("buscar-nopersonal/{noPersonal}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarPorNoPersonal(@PathParam("noPersonal") String noPersonal) {
        return ColaboradorImp.buscarPorNoPersonal(noPersonal);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador obtenerPorId(@PathParam("idColaborador") Integer idColaborador) {
        if (idColaborador != null && idColaborador > 0) {
            return ColaboradorImp.obtenerPorId(idColaborador);
        }
        throw new BadRequestException();
    }
    
    @Path("cambiar-password")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
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
}
