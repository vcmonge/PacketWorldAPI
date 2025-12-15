package pojo;


public class Envio {
    private Integer idEnvio;
    private String noGuia;
    private Double costo;
    private String destinatarioNombre;
    private String destinatarioApellidoPaterno;
    private String destinatarioApellidoMaterno;
    private String numeroDestino;
    private String calleDestino;
    
    // Llaves foráneas (IDs)
    private Integer idEstatusEnvio;
    private Integer idSucursalOrigen;
    private Integer idConductor;
    private Integer idCliente;
    private Integer idColoniaDestino;
    
    // Informacion recolectada de FK
    private String coloniaDestino;
    private String ciudadDestino;
    private String municipioDestino;
    private String estadoDestino;
    private String codigoPostalDestino;
    
    private String conductor;
    
    private String cliente;
    private String clienteTelefono;
    private String clienteCorreo;
    
    private String sucursal;
    private String sucursalDireccion;
    
    private String estatus;
        
    public Envio() {
    }

    public Envio(Integer idEnvio, String noGuia, Double costo, String destinatarioNombre, String destinatarioApellidoPaterno, String destinatarioApellidoMaterno, String numeroDestino, String calleDestino, Integer idEstatusEnvio, Integer idSucursalOrigen, Integer idConductor, Integer idCliente, Integer idColoniaDestino, String coloniaDestino, String ciudadDestino, String municipioDestino, String estadoDestino, String codigoPostalDestino, String conductor, String cliente, String clienteTelefono, String clienteCorreo, String sucursal, String sucursalDireccion, String estatus) {
        this.idEnvio = idEnvio;
        this.noGuia = noGuia;
        this.costo = costo;
        this.destinatarioNombre = destinatarioNombre;
        this.destinatarioApellidoPaterno = destinatarioApellidoPaterno;
        this.destinatarioApellidoMaterno = destinatarioApellidoMaterno;
        this.numeroDestino = numeroDestino;
        this.calleDestino = calleDestino;
        this.idEstatusEnvio = idEstatusEnvio;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idConductor = idConductor;
        this.idCliente = idCliente;
        this.idColoniaDestino = idColoniaDestino;
        this.coloniaDestino = coloniaDestino;
        this.ciudadDestino = ciudadDestino;
        this.municipioDestino = municipioDestino;
        this.estadoDestino = estadoDestino;
        this.codigoPostalDestino = codigoPostalDestino;
        this.conductor = conductor;
        this.cliente = cliente;
        this.clienteTelefono = clienteTelefono;
        this.clienteCorreo = clienteCorreo;
        this.sucursal = sucursal;
        this.sucursalDireccion = sucursalDireccion;
        this.estatus = estatus;
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

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;
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

    public void setIdColoniaDestino(Integer idColoniaDestino) {
        this.idColoniaDestino = idColoniaDestino;
    }

    public void setColoniaDestino(String coloniaDestino) {
        this.coloniaDestino = coloniaDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public void setMunicipioDestino(String municipioDestino) {
        this.municipioDestino = municipioDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public void setClienteCorreo(String clienteCorreo) {
        this.clienteCorreo = clienteCorreo;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public String getCalleDestino() {
        return calleDestino;
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

    public Integer getIdColoniaDestino() {
        return idColoniaDestino;
    }

    public String getColoniaDestino() {
        return coloniaDestino;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public String getMunicipioDestino() {
        return municipioDestino;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public String getConductor() {
        return conductor;
    }

    public String getCliente() {
        return cliente;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public String getClienteCorreo() {
        return clienteCorreo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getCodigoPostalDestino() {
        return codigoPostalDestino;
    }

    public void setCodigoPostalDestino(String codigoPostalDestino) {
        this.codigoPostalDestino = codigoPostalDestino;
    }

    public String getSucursalDireccion() {
        return sucursalDireccion;
    }

    public void setSucursalDireccion(String sucursalDireccion) {
        this.sucursalDireccion = sucursalDireccion;
    }

}
