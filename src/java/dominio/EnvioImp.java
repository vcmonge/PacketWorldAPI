
package dominio;

import dto.EnvioCompletoDTO;
import dto.Respuesta;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
import pojo.EnvioHistorialEstatus;
import pojo.Paquete;
import utilidades.Constantes;
import utilidades.GeneradorNumeroGuia;
import utilidades.Validaciones;

public class EnvioImp {
    
    public static Respuesta generarNumeroGuia() {
        Respuesta respuesta = new Respuesta();
        try {
            String numeroGuia = GeneradorNumeroGuia.generarGuia();
            respuesta.setError(false);
            respuesta.setMensaje(numeroGuia);
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setMensaje("Error al generar el número de guía.");
        }
        return respuesta;
    }
    
    public static List<Envio> obtenerEnviosConductor(int idConductor){
        List<Envio> envios = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try {
                envios = conexionBD.selectList("envio.obtener-envios-conductor", idConductor);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return envios;
    }
    
    public static List<Envio> obtenerEnvio(String noGuia){
        List<Envio> envio = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                envio = conexionBD.selectList("envio.obtener-envio", noGuia);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return envio;
    }
    public static List<Envio> obtenerEnvios(){
        List<Envio> envio = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                envio = conexionBD.selectList("envio.obtener-envios");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return envio;
    }
    
    public static Respuesta crearEnvio(Envio envio){
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        envio.setNoGuia(GeneradorNumeroGuia.generarGuia());
        
        if (conexionBD != null ){
            try {
                int filasAfectadas = conexionBD.insert("envio.crear-envio", envio);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Registro del envío " + envio.getNoGuia() + " exitoso.");
                    System.out.println("\n\nEnvio insertado con id: " + envio.getIdEnvio());
                } else {
                    respuesta.setMensaje("El envío no se pudo guardar, verificar la información.");
                }
            } catch (Exception e) {
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }

   public static Respuesta actualizarEstatus(EnvioHistorialEstatus envioHistorialEstatus){
       Respuesta respuesta = new Respuesta();
       respuesta.setError(true);
       
       // Validación de lógica de negocio: estatus es igual a detenido o cancelado, es obligatorio el comentario
       if (envioHistorialEstatus.getIdEstatusEnvio() == 6 || envioHistorialEstatus.getIdEstatusEnvio() == 4){
           if (Validaciones.esVacio(envioHistorialEstatus.getComentario())){
               respuesta.setMensaje("El comentario es obligatorio para este estatus.");
               return respuesta;
           }
       }

       SqlSession conexionBD = MyBatisUtil.getSession();
       if (conexionBD != null ){
           try {
               HashMap<String, Integer> idEnvioIdEstatus = new LinkedHashMap<>();
               idEnvioIdEstatus.put("idEnvio", envioHistorialEstatus.getIdEnvio());
               idEnvioIdEstatus.put("idEstatusEnvio", envioHistorialEstatus.getIdEstatusEnvio());
               
               int filasAfectadasActualizacion = conexionBD.update("envio.actualizar-estatus-envio", idEnvioIdEstatus);
               int filasAfectadasHistorial = conexionBD.insert("envio.registrar-historial-estatus", envioHistorialEstatus);

                if (filasAfectadasActualizacion > 0 && filasAfectadasHistorial > 0) {
                    conexionBD.commit();
                    respuesta.setError(false);
                    respuesta.setMensaje("Estatus del envío actualizado exitosamente.");
                } else {
                    // Si alguno falló, rollback
                    conexionBD.rollback();
                    respuesta.setMensaje("El estatus del envío no se pudo actualizar, verificar la información.");
                }
           } catch (Exception e) {
               conexionBD.rollback();
               respuesta.setMensaje(e.getMessage());
           } finally {
               conexionBD.close();
           }
       } else {
           respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
       }
       
       return respuesta;
   }
   
public static Respuesta crearEnvioCompleto(EnvioCompletoDTO envioCompleto) {
    Respuesta respuesta = new Respuesta();
    respuesta.setError(true);
    SqlSession conexionBD = MyBatisUtil.getSession();

    if (conexionBD == null) {
        respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        return respuesta;
    }

    try {
        // Se inserta dirección, tienen useGeneratedKeys=true para que guarde el idDireccion
        conexionBD.insert("direccion.crear-direccion", envioCompleto.getDireccion());

        Integer idDireccion = envioCompleto.getDireccion().getIdDireccion();
        if (idDireccion == null) {
            throw new Exception("No se generó ID de dirección");
        }
        // Se guarda en envio la id de la direccion de envio
        envioCompleto.getEnvio().setDestinatarioIdDireccion(idDireccion);
        String noGuia = GeneradorNumeroGuia.generarGuia();
        envioCompleto.getEnvio().setNoGuia(noGuia);

        // Se inserta envío, tienen useGeneratedKeys=true para que guarde el idEnvio
        conexionBD.insert("envio.crear-envio", envioCompleto.getEnvio());
        
        Integer idEnvio = envioCompleto.getEnvio().getIdEnvio();
        if (idEnvio == null) {
            throw new Exception("No se generó ID de envío");
        }
        
        // Validacion por si llega vacia, caso extraño
        if (envioCompleto.getPaquetes() == null || envioCompleto.getPaquetes().isEmpty()) {
            throw new Exception("El envío debe contener al menos un paquete.");
        }
        // Se insertan los paquetes
        for (Paquete paquete : envioCompleto.getPaquetes()) {
            paquete.setIdEnvio(idEnvio);
            conexionBD.insert("paquete.crear-paquete", paquete);
        }

        // Commit si todo salió bien
        conexionBD.commit();
        respuesta.setError(false);
        respuesta.setMensaje("Envío "+noGuia+" registrado correctamente");

    } catch (Exception e) {
        conexionBD.rollback();
        respuesta.setError(true);
        respuesta.setMensaje("Error al registrar el envío: " + e.getMessage());
    } finally {
        conexionBD.close();
    }

    return respuesta;
} 

}
