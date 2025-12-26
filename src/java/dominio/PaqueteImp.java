
package dominio;

import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Paquete;


public class PaqueteImp {
    
    public static List<Paquete> obtenerPaquetesPedido(int idEnvio){
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
    
}
