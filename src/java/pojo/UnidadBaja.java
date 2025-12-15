/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.util.Date;

/**
 *
 * @author julia
 */
public class UnidadBaja {
    private Integer idBaja;
    private Integer idUnidad;
    private String motivoBaja;
    private Date fechaBaja;
    private Integer idColaborador;

    public UnidadBaja() {
    }

    public UnidadBaja(Integer idBaja, Integer idUnidad, String motivoBaja, Date fechaBaja, Integer idColaborador) {
        this.idBaja = idBaja;
        this.idUnidad = idUnidad;
        this.motivoBaja = motivoBaja;
        this.fechaBaja = fechaBaja;
        this.idColaborador = idColaborador;
    }

    public Integer getIdBaja() {
        return idBaja;
    }

    public void setIdBaja(Integer idBaja) {
        this.idBaja = idBaja;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }
}
