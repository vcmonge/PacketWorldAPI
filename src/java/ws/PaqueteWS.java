
package ws;

import com.google.gson.Gson;
import dominio.PaqueteImp;
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
import pojo.Paquete;

@Path("paquete")
public class PaqueteWS {
    
    @Path("obtener-paquetes-envio/{idEnvio}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Paquete> obtenerPaquetesEnvio(@PathParam("idEnvio") Integer idEnvio){
        if( idEnvio != null && idEnvio > 0){
            return PaqueteImp.obtenerPaquetesEnvio(idEnvio);
        }
        throw new BadRequestException();
    }
    
    @Path("registrar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(String json){
        Gson gson = new Gson();
        try {
            Paquete paquete = gson.fromJson(json, Paquete.class);
            return PaqueteImp.registrar(paquete);
        } catch (Exception e) {
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
            Paquete paquete = gson.fromJson(json, Paquete.class);
            return PaqueteImp.editar(paquete);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    @Path("eliminar/{idPaquete}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@PathParam("idPaquete") Integer idPaquete){
        if( idPaquete != null && idPaquete > 0){
            return PaqueteImp.eliminar(idPaquete);
        }
        throw new BadRequestException();
    }

}
