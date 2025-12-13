/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author julia
 */
public class Envio {
    private Integer idEnvio;
    private String noGuia;
    private Double costo;
    
    // Llaves foráneas (IDs)
    private Integer idEstatusEnvio;
    private Integer idSucursalOrigen;
    private Integer idConductor;
    private Integer idCliente;
    private Integer idDestinatario;
    private Integer idDireccionDestino;

    public Envio() {
    }

    public Envio(Integer idEnvio, String noGuia, Double costo, Integer idEstatusEnvio, Integer idSucursalOrigen, Integer idConductor, Integer idCliente, Integer idDestinatario, Integer idDireccionDestino) {
        this.idEnvio = idEnvio;
        this.noGuia = noGuia;
        this.costo = costo;
        this.idEstatusEnvio = idEstatusEnvio;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idConductor = idConductor;
        this.idCliente = idCliente;
        this.idDestinatario = idDestinatario;
        this.idDireccionDestino = idDireccionDestino;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getNoGuia() {
        return noGuia;
    }

    public void setNoGuia(String noGuia) {
        this.noGuia = noGuia;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Integer getIdEstatusEnvio() {
        return idEstatusEnvio;
    }

    public void setIdEstatusEnvio(Integer idEstatusEnvio) {
        this.idEstatusEnvio = idEstatusEnvio;
    }

    public Integer getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public void setIdSucursalOrigen(Integer idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Integer idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Integer getIdDireccionDestino() {
        return idDireccionDestino;
    }

    public void setIdDireccionDestino(Integer idDireccionDestino) {
        this.idDireccionDestino = idDireccionDestino;
    }
}
