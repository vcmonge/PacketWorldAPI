
package dominio;

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
    
    public static List<Direccion> obtenerDireccionCodigoPostal(int codigoPostal){
        List<Direccion> colonias = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD != null) {
            try {
                colonias = conexionBD.selectList("direccion.obtener-colonias-codigo_postal", codigoPostal);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return colonias;
    }
    public static Direccion obtenerDireccionPorId(Integer idDireccion) {
        Direccion direccion = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                direccion = conexionBD.selectOne("direccion.obtener-direccion-id", idDireccion);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return direccion;
    }
    public static Respuesta obtenerCodigoPostalSucursal(Integer idSucursal){
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if ( conexionBD != null ){
            try {
                Direccion direccion = conexionBD.selectOne("obtener-cp-sucursal", idSucursal);
                if( direccion != null ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Codigo postal encontrado.");
                    respuesta.setValor(direccion.getCodigoPostal().toString());
                } else {
                    respuesta.setMensaje("Codigo postal de sucursal no encontrado");
                }
            } catch (Exception e) {
                
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        }
        return respuesta;
    }
}
