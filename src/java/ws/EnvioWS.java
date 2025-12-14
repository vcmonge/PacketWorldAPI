
package ws;

import dominio.EnvioImp;
import dto.Respuesta;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
