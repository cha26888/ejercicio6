/** Certificador FEL ficticio; emite un XML de demostracion. */
public class APIFacturacion extends IntegracionAPI implements Documentable, Auditable {
    private final String nitCertificador;
    public APIFacturacion(String id, String proveedor, String endpoint, boolean conectado, int latenciaMs, String nitCertificador) {
        super(id, proveedor, endpoint, conectado, latenciaMs);
        this.nitCertificador = textoRequerido(nitCertificador, "NIT certificador");
    }
    public String getNitCertificador() { return nitCertificador; }
    @Override public String getTipo() { return "FEL"; }
    @Override public String getConfiguracion() { return super.getConfiguracion() + "\nNIT certificador ficticio: " + nitCertificador; }
    @Override public String generarDocumento(String referencia) {
        return "<facturaFEL certificador=\"" + nitCertificador + "\" referencia=\"" + referencia + "\"/> (simulado, no valido legalmente)";
    }
    @Override public String consultarAuditoria() { return "Auditoria FEL simulada de " + getId() + ": sin incidencias."; }
}
