
package ws;

import dominio.EnvioImp;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Envio;
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
    @Produces(MediaType.APPLICATION_JSON)
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
}
