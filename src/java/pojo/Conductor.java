/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author julia
 */
public class Conductor extends Colaborador {
    // Mapeo de la columna 'numero_licencia'
    private String numeroLicencia;

    public Conductor() {
        super(); // Inicializa los atributos del padre
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Licencia: " + numeroLicencia + ")";
    }
}
