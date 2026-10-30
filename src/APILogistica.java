/** Proveedor logistico ficticio; no admite cobros ni reintegros. */
public class APILogistica extends IntegracionAPI implements Documentable, Auditable {
    private final String zonaCobertura;
    public APILogistica(String id, String proveedor, String endpoint, boolean conectado, int latenciaMs, String zonaCobertura) {
        super(id, proveedor, endpoint, conectado, latenciaMs);
        this.zonaCobertura = textoRequerido(zonaCobertura, "Zona de cobertura");
    }
    public String getZonaCobertura() { return zonaCobertura; }
    @Override public String getTipo() { return "LOGISTICA"; }
    @Override public String getConfiguracion() { return super.getConfiguracion() + "\nZona de cobertura: " + zonaCobertura; }
    @Override public String generarDocumento(String referencia) {
        return "<guia proveedor=\"" + getId() + "\" referencia=\"" + referencia + "\" zona=\"" + zonaCobertura + "\"/> (simulado)";
    }
    @Override public String consultarAuditoria() { return "Auditoria logistica simulada de " + getId() + ": sin incidencias."; }
    public String rastrear(String guia) { return "Guia " + guia + ": en transito (simulado)."; }
    public String cotizar(String destino) { return "Cotizacion ficticia para " + destino + ": Q35.00."; }
}
