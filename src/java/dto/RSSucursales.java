package dto;

import java.util.List;
import pojo.Sucursal;

public class RSSucursales extends Respuesta {
    
    private List<Sucursal> sucursales;

    public RSSucursales() {
        super();
    }

    public RSSucursales(boolean error, String mensaje, List<Sucursal> sucursales) {
        super(error, mensaje);
        this.sucursales = sucursales;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
}