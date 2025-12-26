/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import pojo.Sucursal;

/**
 *
 * @author HECTO
 */
public class RSSucursal {
    private boolean error;
    private String mensaje;
    private Sucursal sucursal;

    public RSSucursal() {
    }

    public RSSucursal(boolean error, String mensaje, Sucursal sucursal) {
        this.error = error;
        this.mensaje = mensaje;
        this.sucursal = sucursal;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
}