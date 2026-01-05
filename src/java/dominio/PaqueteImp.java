
package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Paquete;
import utilidades.Constantes;


public class PaqueteImp {
    
    public static List<Paquete> obtenerPaquetesEnvio(int idEnvio){
        List<Paquete> paquetes = null;

        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                 paquetes = conexionBD.selectList("paquete.obtener-paquetes-envio", idEnvio);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return paquetes;
    }
    
    public static Respuesta registrar(Paquete paquete){
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if( conexionBD != null ){
            try {
                int filasAfectadas = conexionBD.insert("paquete.crear-paquete", paquete);
                conexionBD.commit();
                if( filasAfectadas > 0 ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Paquete añadido al envío.");
                } else {
                    respuesta.setMensaje("Lo sentimos no se pudo añadir el paquete.");
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
    
    public static Respuesta editar(Paquete paquete){
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if( conexionBD != null ){
            try {
                int filasAfectadas = conexionBD.update("paquete.modificar-paquete", paquete);
                conexionBD.commit();
                if( filasAfectadas > 0 ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Información del paquete actualizada.");
                } else {
                    respuesta.setMensaje("Lo sentimos no se pudo actualizar la iformación del paquete.");
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
    
    public static Respuesta eliminar(Integer idPaquete){
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if( conexionBD != null ){
            try {
                int filasAfectadas = conexionBD.delete("paquete.eliminar-paquete", idPaquete);
                conexionBD.commit();
                if( filasAfectadas > 0 ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Paquete eliminado.");
                } else {
                    respuesta.setMensaje("Lo sentimos no se pudo eliminar el paquete.");
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
    
}
