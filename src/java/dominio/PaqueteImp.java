
package dominio;

import dto.RSPaquete;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Paquete;
import utilidades.Constantes;


public class PaqueteImp {
    
    public static RSPaquete obtenerPaquetesPedido(int idEnvio){
        RSPaquete respuesta = new RSPaquete();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                List<Paquete> paquetes = conexionBD.selectList("paquete.obtener-paquetes-envio", idEnvio);
                if (paquetes.isEmpty()){
                    respuesta.setMensaje("El envio no tiene paquetes.");
                } else {
                    respuesta.setError(false);
                    respuesta.setMensaje(paquetes.size() + " paquetes obtenidos.");
                }
                respuesta.setPaquetes(paquetes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        
        return respuesta;
    }
    
}
