/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

import dominio.CatalogoImp;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.EstatusEnvio;
import pojo.Rol;
import pojo.TipoUnidad;

/**
 *
 * @author julia
 */
@Path("catalogo")
public class CatalogoWS {
   @Path("obtener-roles")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<Rol> obtenerRolesSistema(){
       return CatalogoImp.obtenerRolesSistema();
   }
   
   @Path("obtener-estatus-envios")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<EstatusEnvio> obtenerEstatusEnviosSistema() {
       return CatalogoImp.obtenerEstatusEnviosSistema();
   }
   
   @Path("obtener-tipo-unidades")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<TipoUnidad> obtenerTipoUnidadesSistema() {
       return CatalogoImp.obtenerTipoUnidadesSistema();
   }
}
