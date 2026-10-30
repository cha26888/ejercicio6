import java.util.ArrayList;
import java.util.Collections;

/** Controlador MVC. Unica coleccion polimorfica de integraciones. No imprime. */
public class ControladorIntegraciones {
    private final ArrayList<IntegracionAPI> integraciones;

    public ControladorIntegraciones() {
        integraciones = new ArrayList<>();
        // Datos de ejemplo definidos en la hoja Carga inicial del analisis.
        registrar(new APIPago("PAG-01", "Tarjeta A", "https://demo.local/pag01", true, 75, "GTQ"));
        registrar(new APIPago("PAG-02", "Tarjeta B", "https://demo.local/pag02", true, 92, "USD"));
        registrar(new APIPago("PAG-03", "Billetera", "https://demo.local/pag03", true, 65, "GTQ"));
        registrar(new APIPago("PAG-04", "Transferencia", "https://demo.local/pag04", false, 145, "GTQ"));
        registrar(new APILogistica("LOG-01", "Envios Express", "https://demo.local/log01", true, 110, "Guatemala"));
        registrar(new APILogistica("LOG-02", "Mensajeria Local", "https://demo.local/log02", true, 82, "Capital"));
        registrar(new APILogistica("LOG-03", "Paqueteria Regional", "https://demo.local/log03", false, 165, "Centroamerica"));
        registrar(new APIFacturacion("FEL-01", "Certificador Uno", "https://demo.local/fel01", true, 125, "NIT-FICT-001"));
        registrar(new APIFacturacion("FEL-02", "Certificador Dos", "https://demo.local/fel02", true, 100, "NIT-FICT-002"));
        registrar(new APIFacturacion("FEL-03", "Certificador Tres", "https://demo.local/fel03", true, 130, "NIT-FICT-003"));
    }

    public boolean registrar(IntegracionAPI api) {
        if (api == null || buscar(api.getId()) != null) return false;
        return integraciones.add(api);
    }
    public IntegracionAPI buscar(String id) {
        if (id == null) return null;
        for (IntegracionAPI api : integraciones) {
            if (api.getId().equalsIgnoreCase(id.trim())) return api;
        }
        return null;
    }
    public String listar() {
        StringBuilder texto = new StringBuilder("ID       | PROVEEDOR             | TIPO        | ESTADO      | LATENCIA\n");
        texto.append("--------------------------------------------------------------------------\n");
        for (IntegracionAPI api : integraciones) texto.append(api).append('\n');
        return texto.toString();
    }
    public void ordenarPorLatencia() { Collections.sort(integraciones); }

    private String validarOperacion(IntegracionAPI api) {
        if (api == null) return "Integracion no encontrada.";
        if (!api.getConectado()) return "Integracion desconectada; operacion ignorada.";
        return null;
    }
    private String validarMonto(double monto) {
        return !Double.isFinite(monto) || monto <= 0 ? "El monto debe ser positivo y finito." : null;
    }
    private String validarReferencia(String referencia) {
        return referencia == null || referencia.trim().isEmpty() ? "La referencia no puede estar vacia." : null;
    }
    // Overloading y despacho por interfaz; sin cast inseguro ni excepcion para incompatibilidad.
    public String procesarCobro(Transaccional servicio, double monto) {
        if (!(servicio instanceof IntegracionAPI)) return "Servicio no registrado o incompatible.";
        IntegracionAPI api = (IntegracionAPI) servicio;
        if (buscar(api.getId()) != api) return "Servicio no registrado.";
        String error = validarOperacion(api);
        if (error != null) return error;
        error = validarMonto(monto);
        return error != null ? error : servicio.cobrar(monto);
    }
    public String procesarCobro(String id, double monto) {
        IntegracionAPI api = buscar(id);
        if (api == null) return "Integracion no encontrada.";
        if (!(api instanceof Transaccional)) return "Cobro no disponible para " + api.getTipo() + "; solicitud ignorada.";
        return procesarCobro((Transaccional) api, monto);
    }
    public String procesarReintegro(Transaccional servicio, double monto) {
        if (!(servicio instanceof IntegracionAPI)) return "Servicio no registrado o incompatible.";
        IntegracionAPI api = (IntegracionAPI) servicio;
        if (buscar(api.getId()) != api) return "Servicio no registrado.";
        String error = validarOperacion(api);
        if (error != null) return error;
        error = validarMonto(monto);
        return error != null ? error : servicio.reintegrar(monto);
    }
    public String procesarReintegro(String id, double monto) {
        IntegracionAPI api = buscar(id);
        if (api == null) return "Integracion no encontrada.";
        if (!(api instanceof Transaccional)) return "Reintegro no disponible para " + api.getTipo() + "; solicitud ignorada.";
        return procesarReintegro((Transaccional) api, monto);
    }
    public String procesarDocumento(Documentable servicio, String referencia) {
        if (!(servicio instanceof IntegracionAPI)) return "Servicio no registrado o incompatible.";
        IntegracionAPI api = (IntegracionAPI) servicio;
        if (buscar(api.getId()) != api) return "Servicio no registrado.";
        String error = validarOperacion(api);
        if (error != null) return error;
        error = validarReferencia(referencia);
        return error != null ? error : servicio.generarDocumento(referencia.trim());
    }
    public String procesarDocumento(String id, String referencia) {
        IntegracionAPI api = buscar(id);
        if (api == null) return "Integracion no encontrada.";
        if (!(api instanceof Documentable)) return "Documento no disponible para " + api.getTipo() + "; solicitud ignorada.";
        return procesarDocumento((Documentable) api, referencia);
    }
    public String consultarAuditoria(Auditable servicio) {
        if (!(servicio instanceof IntegracionAPI)) return "Servicio no registrado o incompatible.";
        IntegracionAPI api = (IntegracionAPI) servicio;
        if (buscar(api.getId()) != api) return "Servicio no registrado.";
        String error = validarOperacion(api);
        return error != null ? error : servicio.consultarAuditoria();
    }
    public String consultarAuditoria(String id) {
        IntegracionAPI api = buscar(id);
        if (api == null) return "Integracion no encontrada.";
        if (!(api instanceof Auditable)) return "Auditoria no disponible; solicitud ignorada.";
        return consultarAuditoria((Auditable) api);
    }
}
