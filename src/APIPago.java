/** Pasarela financiera ficticia; implementa dos contratos. */
public class APIPago extends IntegracionAPI implements Transaccional, Auditable {
    private final String moneda;
    public APIPago(String id, String proveedor, String endpoint, boolean conectado, int latenciaMs, String moneda) {
        super(id, proveedor, endpoint, conectado, latenciaMs);
        this.moneda = textoRequerido(moneda, "Moneda");
    }
    public String getMoneda() { return moneda; }
    @Override public String getTipo() { return "PAGO"; }
    @Override public String getConfiguracion() { return super.getConfiguracion() + "\nMoneda: " + moneda; }
    @Override public String cobrar(double monto) { return String.format("Cobro simulado de %.2f %s por %s.", monto, moneda, getProveedor()); }
    @Override public String reintegrar(double monto) { return String.format("Reintegro simulado de %.2f %s por %s.", monto, moneda, getProveedor()); }
    @Override public String consultarAuditoria() { return "Auditoria simulada de pagos para " + getId() + ": sin incidencias."; }
}
