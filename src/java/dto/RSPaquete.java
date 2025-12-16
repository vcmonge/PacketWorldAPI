
package dto;

import java.util.List;
import pojo.Paquete;


public class RSPaquete {
    private boolean error;
    private String mensaje;
    private List<Paquete> paquetes;

    public RSPaquete() {
    }

    public RSPaquete(boolean error, String mensaje, List<Paquete> paquetes) {
        this.error = error;
        this.mensaje = mensaje;
        this.paquetes = paquetes;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

}
