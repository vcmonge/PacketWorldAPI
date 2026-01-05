
package ws;

import dominio.EnvioHistorialEstatusImp;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.EnvioHistorialEstatus;
import utilidades.Validaciones;

@Path("envio-historial-estatus")
public class EnvioHistorialEstatusWS {
    
    @Path("obtener/{noGuia}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<EnvioHistorialEstatus> obtenerPaquetesEnvio(@PathParam("noGuia") String noGuia){
        if ( !Validaciones.esVacio(noGuia) ){
            return EnvioHistorialEstatusImp.obtenerPorNoGuia(noGuia);
        }
        throw new BadRequestException();
    }
    
}
