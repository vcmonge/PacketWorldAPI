
package dominio;

import java.util.List;
import javax.ws.rs.NotFoundException;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.EnvioHistorialEstatus;

public class EnvioHistorialEstatusImp {
    
    public static List<EnvioHistorialEstatus> obtenerPorNoGuia(String noGuia){
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try {
                List<EnvioHistorialEstatus> historial = conexionBD.selectList("EnvioHistorialEstatus.obtener-por-noguia", noGuia);

                if (historial.isEmpty()) {
                    throw new NotFoundException("No existe historial para la guía: " + noGuia);
                }

                return historial;

            } finally {
                conexionBD.close();
            }
        }
        throw new RuntimeException("No se pudo obtener conexión a la BD");
    }
}
