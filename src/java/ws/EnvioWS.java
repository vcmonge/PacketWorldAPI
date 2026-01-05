
package ws;

import com.google.gson.Gson;
import dominio.EnvioImp;
import dto.EnvioCompletoDTO;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Envio;
import pojo.EnvioHistorialEstatus;
import utilidades.Validaciones;

@Path("envio")
public class EnvioWS {
    
    public EnvioWS() {
        
    }
    
    @Path("generar-numero-guia")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta generarNumeroGuia(){
        return EnvioImp.generarNumeroGuia();
    }
    
    @Path("obtener-envios-conductor/{idConductor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Envio> obtenerEnviosConductor(@PathParam("idConductor") String idConductor){
        if(Validaciones.esVacio(idConductor)){
            throw new BadRequestException();
        }
        if(!Validaciones.esNumerico(idConductor)){
            throw new BadRequestException();
        }
        
        int id = Integer.parseInt(idConductor);
        
        return EnvioImp.obtenerEnviosConductor(id);
    }
    
    @Path("obtener-envio/{noGuia}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Envio> obtenerEnvio(@PathParam("noGuia") String noGuia){
        if (Validaciones.esVacio(noGuia)){
            throw new BadRequestException();
        }
        return EnvioImp.obtenerEnvio(noGuia);
    }
    @Path("obtener-envios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Envio> obtenerEnvios(){
        return EnvioImp.obtenerEnvios();
    }
    
    @Path("crear-envio")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta crearEnvio(String json){
        Gson gson = new Gson();
        try {
            Envio envio = gson.fromJson(json, Envio.class);
            return EnvioImp.crearEnvio(envio);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    @Path("actualizar-estatus")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizarEstatus(String json){
       Gson gson = new Gson();
       try {
           EnvioHistorialEstatus envioHistorialEstatus = gson.fromJson(json, EnvioHistorialEstatus.class);
           return EnvioImp.actualizarEstatus(envioHistorialEstatus);
       } catch (Exception e) {
           throw new BadRequestException(e.getMessage());
       }
    }
    
    @Path("crear-envio-completo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta crearEnvioCompleto(String json){
        Gson gson = new Gson();
        try {
            EnvioCompletoDTO envioCompleto = gson.fromJson(json, EnvioCompletoDTO.class);
            return EnvioImp.crearEnvioCompleto(envioCompleto);
        } catch(Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    @Path("editar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editar(String json){
        Gson gson = new Gson();
        try {
            Envio envio = gson.fromJson(json, Envio.class);
            return EnvioImp.editar(envio);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    @Path("actualizar-costo")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizarCosto(String json){
        Gson gson = new Gson();
        try {
            Envio envio = gson.fromJson(json, Envio.class);
            return EnvioImp.actualizarCosto(envio);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
