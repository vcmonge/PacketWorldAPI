
package dto;

import java.util.List;
import pojo.Direccion;

public class RSColonias {
    private boolean error;
    private String mensaje;
    private List<Direccion> colonias;

    public RSColonias() {
    }

    public RSColonias(boolean error, String mensaje, List<Direccion> colonias) {
        this.error = error;
        this.mensaje = mensaje;
        this.colonias = colonias;
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<Direccion> getColonias() {
        return colonias;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setColonias(List<Direccion> colonias) {
        this.colonias = colonias;
    }

}
