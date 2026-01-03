/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

import dominio.AutenticacionImp;
import dto.RSAutenticacionColaborador;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author julia
 */
@Path("autenticacion")
public class AutenticacionWS {
    
    @Path("movil")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public RSAutenticacionColaborador autenticarConductor(
            @FormParam("noPersonal") String noPersonal,
            @FormParam("password") String password) {
        if (noPersonal != null && !noPersonal.isEmpty()
                && password != null && !password.isEmpty()) {
            return AutenticacionImp.autenticacionConductor(noPersonal, password);
        }
        throw new BadRequestException();
    }
    
    @Path("escritorio")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RSAutenticacionColaborador autenticarEscritorio(
            @FormParam("noPersonal") String noPersonal,
            @FormParam("password") String password) {
        if (noPersonal != null && !noPersonal.isEmpty()
                && password != null && !password.isEmpty()) {
            return AutenticacionImp.iniciarSesionEscritorio(noPersonal, password);
        }
        throw new BadRequestException();
    }
    
    
}
