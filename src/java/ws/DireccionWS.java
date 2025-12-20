
package ws;

import com.google.gson.Gson;
import dominio.DireccionImp;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Direccion;
import utilidades.Validaciones;

@Path("direccion")
public class DireccionWS {
   
    @Path("crear-direccion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta crearDireccion(String json){
        Gson gson = new Gson();
        try {
            Direccion direccion = gson.fromJson(json, Direccion.class);
           
            if (Validaciones.esVacio(direccion.getCalle())) {
                throw new BadRequestException("La calle es obligatoria");
            }

            if (direccion.getIdColonia() == null) {
                throw new BadRequestException("El idColonia es obligatorio");
            }

            return DireccionImp.crearDireccion(direccion);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    
    @Path("obtener-direccion-codigo-postal/{codigoPostal}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Direccion> obtenerDireccionCodigoPostal(@PathParam("codigoPostal") String codigoPostal){
         if (Validaciones.esVacio(codigoPostal)) {
             throw new BadRequestException();
         }

         if (!Validaciones.esNumericoConLongitud(codigoPostal, 5)) {
             throw new BadRequestException();
         }

         int cp = Integer.parseInt(codigoPostal);

         return DireccionImp.obtenerDireccionCodigoPostal(cp);
    }
   
}
