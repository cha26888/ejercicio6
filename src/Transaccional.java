/** Contrato para operaciones financieras simuladas. */
public interface Transaccional {
    String cobrar(double monto);
    String reintegrar(double monto);
}
