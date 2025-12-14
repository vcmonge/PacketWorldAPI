package pojo;


public class Envio {
    private Integer idEnvio;
    private String noGuia;
    private Double costo;
    private String destinatarioNombre;
    private String destinatarioApellidoPaterno;
    private String destinatarioApellidoMaterno;
    
    // Llaves foráneas (IDs)
    private Integer idEstatusEnvio;
    private Integer idSucursalOrigen;
    private Integer idConductor;
    private Integer idCliente;
    private Integer idDireccionDestino;

    public Envio() {
    }

    public Envio(Integer idEnvio, String noGuia, Double costo, String destinatarioNombre, String destinatarioApellidoPaterno, String destinatarioApellidoMaterno, Integer idEstatusEnvio, Integer idSucursalOrigen, Integer idConductor, Integer idCliente, Integer idDireccionDestino) {
        this.idEnvio = idEnvio;
        this.noGuia = noGuia;
        this.costo = costo;
        this.destinatarioNombre = destinatarioNombre;
        this.destinatarioApellidoPaterno = destinatarioApellidoPaterno;
        this.destinatarioApellidoMaterno = destinatarioApellidoMaterno;
        this.idEstatusEnvio = idEstatusEnvio;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idConductor = idConductor;
        this.idCliente = idCliente;
        this.idDireccionDestino = idDireccionDestino;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public String getNoGuia() {
        return noGuia;
    }

    public Double getCosto() {
        return costo;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public String getDestinatarioApellidoPaterno() {
        return destinatarioApellidoPaterno;
    }

    public String getDestinatarioApellidoMaterno() {
        return destinatarioApellidoMaterno;
    }

    public Integer getIdEstatusEnvio() {
        return idEstatusEnvio;
    }

    public Integer getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public Integer getIdDireccionDestino() {
        return idDireccionDestino;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setNoGuia(String noGuia) {
        this.noGuia = noGuia;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public void setDestinatarioNombre(String destinatarioNombre) {
        this.destinatarioNombre = destinatarioNombre;
    }

    public void setDestinatarioApellidoPaterno(String destinatarioApellidoPaterno) {
        this.destinatarioApellidoPaterno = destinatarioApellidoPaterno;
    }

    public void setDestinatarioApellidoMaterno(String destinatarioApellidoMaterno) {
        this.destinatarioApellidoMaterno = destinatarioApellidoMaterno;
    }

    public void setIdEstatusEnvio(Integer idEstatusEnvio) {
        this.idEstatusEnvio = idEstatusEnvio;
    }

    public void setIdSucursalOrigen(Integer idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdDireccionDestino(Integer idDireccionDestino) {
        this.idDireccionDestino = idDireccionDestino;
    }

}
