/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author julia
 */
public class EstatusEnvio {
    private Integer idEstatusEnvio;
    private String nombre;

    public EstatusEnvio() {
    }

    public EstatusEnvio(Integer idEstatusEnvio, String nombre) {
        this.idEstatusEnvio = idEstatusEnvio;
        this.nombre = nombre;
    }

    public Integer getIdEstatusEnvio() {
        return idEstatusEnvio;
    }

    public void setIdEstatusEnvio(Integer idEstatusEnvio) {
        this.idEstatusEnvio = idEstatusEnvio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
