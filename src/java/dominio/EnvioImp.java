
package dominio;

import dto.Respuesta;
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
}
