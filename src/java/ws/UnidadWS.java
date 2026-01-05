/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

import com.google.gson.Gson;
import dominio.UnidadImp;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pojo.TipoUnidad;
import pojo.Unidad;
import pojo.UnidadBaja;

@Path("unidades")
public class UnidadWS {

    // 1. OBTENER TODAS (Para llenar la tabla)
    @Path("obtener-todas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> obtenerTodas() {
        return UnidadImp.obtenerUnidades();
    }

    // 2. OBTENER TIPOS (Para el ComboBox de registro)
    @Path("obtener-tipos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoUnidad> obtenerTipos() {
        return UnidadImp.obtenerTiposUnidad();
    }

    // 3. REGISTRAR UNIDAD
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta registrar(String json) {
        Gson gson = new Gson();
        Unidad unidad = gson.fromJson(json, Unidad.class);

        // Validaciones básicas antes de tocar la BD
        if (unidad.getVin() == null || unidad.getVin().isEmpty()) {
            return new Respuesta(true, "El VIN es obligatorio.");
        }
        if (unidad.getIdTipoUnidad() == null || unidad.getIdTipoUnidad() <= 0) {
            return new Respuesta(true, "Debe seleccionar un tipo de unidad válido.");
        }

        return UnidadImp.registrarUnidad(unidad);
    }

    // 4. EDITAR UNIDAD
    @Path("editar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta editar(String json) {
        Gson gson = new Gson();
        Unidad unidad = gson.fromJson(json, Unidad.class);

        if (unidad.getIdUnidad() == null) {
            return new Respuesta(true, "El ID de la unidad es necesario para editar.");
        }

        return UnidadImp.editarUnidad(unidad);
    }

    // 5. DAR DE BAJA 
    // Usamos PUT porque enviamos un objeto JSON con el motivo, no un simple ID en la URL.
    @Path("dar-baja")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta darBaja(String json) {
        Gson gson = new Gson();
        UnidadBaja unidadBaja = gson.fromJson(json, UnidadBaja.class);

        // Validaciones críticas
        if (unidadBaja.getIdUnidad() == null) {
            return new Respuesta(true, "El ID de la unidad es obligatorio.");
        }
        if (unidadBaja.getIdColaborador() == null) {
            return new Respuesta(true, "Se requiere identificar al colaborador que realiza la baja.");
        }
        if (unidadBaja.getMotivoBaja() == null || unidadBaja.getMotivoBaja().trim().isEmpty()) {
            return new Respuesta(true, "El motivo de baja es obligatorio.");
        }

        return UnidadImp.darBajaUnidad(unidadBaja);
    }
    
    @Path("buscar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> buscarUnidad(@QueryParam("texto") String busqueda) {
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            return UnidadImp.buscarUnidad(busqueda);
        } else {
            return UnidadImp.obtenerUnidades();
        }
    }
    
    @Path("obtener-disponibles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> obtenerUnidadesDisponibles() {
        return UnidadImp.obtenerDisponibles();
    }
}