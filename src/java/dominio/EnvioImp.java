
package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
import utilidades.Constantes;
import utilidades.GeneradorNumeroGuia;

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
    
    public static Envio obtenerEnvio(String noGuia){
        Envio envio = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                envio = conexionBD.selectOne("envio.obtener-envio", noGuia);
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
}
