
package ws;

import dominio.PaqueteImp;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Paquete;
import utilidades.Validaciones;

@Path("paquete")
public class PaqueteWS {
    
    @Path("obtener-paquetes-envio/{idEnvio}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paquete> obtenerPaquetesPedido(@PathParam("idEnvio") String idEnvio){
        if (Validaciones.esVacio(idEnvio)){
            throw new BadRequestException();
        }
        if (!Validaciones.esNumerico(idEnvio)){
            throw new BadRequestException();
        }
        int id = Integer.parseInt(idEnvio);
        return PaqueteImp.obtenerPaquetesPedido(id);
    }

}
