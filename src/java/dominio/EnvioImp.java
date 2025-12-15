
package dominio;

import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
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
            respuesta.setMensaje("Error al generar el número de guía");
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
}
