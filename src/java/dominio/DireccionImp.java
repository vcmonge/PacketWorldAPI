
package dominio;

import dto.RSColonias;
import dto.Respuesta;
import java.util.List;
import modelo.mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Direccion;
import utilidades.Constantes;

public class DireccionImp {
    
    public static Respuesta crearDireccion(Direccion direccion){
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.insert("direccion.crear-direccion", direccion);
                conexionBD.commit();
                if (filasAfectadas == 1) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Dirección guardada");
                } else {
                    respuesta.setMensaje("La dirección no se pudo guardar, verifique la información");
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
    
    public static RSColonias obtenerColoniasCodigoPostal(int codigoPostal){
        RSColonias respuesta = new RSColonias();
        respuesta.setError(true);
        List<Direccion> colonias = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                colonias = conexionBD.selectList("direccion.obtener-colonias-codigo_postal", codigoPostal);
                if (!colonias.isEmpty()) {
                    respuesta.setError(false);
                    respuesta.setColonias(colonias);
                } else {
                    respuesta.setMensaje("No se encontró información asociada a ese código postal.");
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
