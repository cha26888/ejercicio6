import java.util.Objects;

/** Modelo abstracto de una integracion externa; orden natural por latencia. */
public abstract class IntegracionAPI implements Comparable<IntegracionAPI> {
    private final String id;
    private final String proveedor;
    private String endpoint;
    private boolean conectado;
    private int latenciaMs;

    public IntegracionAPI(String id, String proveedor, String endpoint, boolean conectado, int latenciaMs) {
        this.id = textoRequerido(id, "ID");
        this.proveedor = textoRequerido(proveedor, "Proveedor");
        setEndpoint(endpoint);
        this.conectado = conectado;
        setLatenciaMs(latenciaMs);
    }

    protected static String textoRequerido(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacio.");
        }
        return valor.trim();
    }

    public String getId() { return id; }
    public String getProveedor() { return proveedor; }
    public String getEndpoint() { return endpoint; }
    public boolean getConectado() { return conectado; }
    public int getLatenciaMs() { return latenciaMs; }
    public void setEndpoint(String valor) { endpoint = textoRequerido(valor, "Endpoint"); }
    public void setConectado(boolean valor) { conectado = valor; }
    public void setLatenciaMs(int valor) {
        if (valor < 0) { throw new IllegalArgumentException("La latencia no puede ser negativa."); }
        latenciaMs = valor;
    }
    public abstract String getTipo();
    public String getConfiguracion() {
        return "ID: " + id + "\nProveedor: " + proveedor + "\nTipo: " + getTipo()
            + "\nEndpoint (simulado): " + endpoint + "\nConectado: " + (conectado ? "SI" : "NO")
            + "\nLatencia promedio: " + latenciaMs + " ms";
    }
    @Override public int compareTo(IntegracionAPI otra) {
        int resultado = Integer.compare(latenciaMs, otra.latenciaMs);
        return resultado != 0 ? resultado : id.compareTo(otra.id);
    }
    @Override public String toString() {
        return String.format("%-8s | %-21s | %-11s | %-11s | %4d ms", id, proveedor, getTipo(), conectado ? "CONECTADO" : "DESCONECTADO", latenciaMs);
    }
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IntegracionAPI)) return false;
        return id.equals(((IntegracionAPI) obj).id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
