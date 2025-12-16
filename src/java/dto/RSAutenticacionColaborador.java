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
public class RSAutenticacionColaborador {
    private String mensaje;
    private boolean error;
    private Colaborador colaborador;

    public RSAutenticacionColaborador() {
    }

    public RSAutenticacionColaborador(String mensaje, boolean error, Colaborador colaborador) {
        this.mensaje = mensaje;
        this.error = error;
        this.colaborador = colaborador;
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

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
