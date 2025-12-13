/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.time.LocalDateTime;

/**
 *
 * @author julia
 */
public class EnvioHistorialEstatus {
    private Integer idHistorial;
    private Integer idEnvio;
    private Integer idColaborador;
    private Integer idEstatusEnvio;
    private String comentario; 
    private LocalDateTime fechaHora;

    public EnvioHistorialEstatus() {
    }

    public EnvioHistorialEstatus(Integer idHistorial, Integer idEnvio, Integer idColaborador, Integer idEstatusEnvio, String comentario, LocalDateTime fechaHora) {
        this.idHistorial = idHistorial;
        this.idEnvio = idEnvio;
        this.idColaborador = idColaborador;
        this.idEstatusEnvio = idEstatusEnvio;
        this.comentario = comentario;
        this.fechaHora = fechaHora;
    }

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdEstatusEnvio() {
        return idEstatusEnvio;
    }

    public void setIdEstatusEnvio(Integer idEstatusEnvio) {
        this.idEstatusEnvio = idEstatusEnvio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
