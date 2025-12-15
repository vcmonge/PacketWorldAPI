/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import pojo.Colaborador;

/**
 *
 * @author julia
 */
public class RSAutenticacionConductor {
    private String mensaje;
    private boolean error;
    private Colaborador conductor;

    public RSAutenticacionConductor() {
    }

    public RSAutenticacionConductor(String mensaje, boolean error, Colaborador conductor) {
        this.mensaje = mensaje;
        this.error = error;
        this.conductor = conductor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Colaborador getConductor() {
        return conductor;
    }

    public void setConductor(Colaborador conductor) {
        this.conductor = conductor;
    }    
}
