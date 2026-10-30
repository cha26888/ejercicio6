import java.util.Scanner;

/** Vista de consola y driver: toda salida del programa ocurre en esta clase. */
public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        ControladorIntegraciones controlador = new ControladorIntegraciones();
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== GESTOR DE INTEGRACIONES (SIMULACION) ===");
            System.out.println("1. Listar integraciones y estados");
            System.out.println("2. Buscar por ID y ver configuracion");
            System.out.println("3. Ordenar por latencia y listar");
            System.out.println("4. Simular cobro");
            System.out.println("5. Simular reintegro");
            System.out.println("6. Generar documento simulado");
            System.out.println("7. Consultar auditoria");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    System.out.println(controlador.listar());
                    break;
                case 2:
                    String idConsulta = leerTexto("ID: ");
                    IntegracionAPI encontrada = controlador.buscar(idConsulta);
                    System.out.println(encontrada == null ? "Integracion no encontrada." : encontrada.getConfiguracion());
                    break;
                case 3:
                    controlador.ordenarPorLatencia();
                    System.out.println("Ordenado por latencia ascendente.\n" + controlador.listar());
                    break;
                case 4:
                    System.out.println(controlador.procesarCobro(leerTexto("ID: "), leerDouble()));
                    break;
                case 5:
                    System.out.println(controlador.procesarReintegro(leerTexto("ID: "), leerDouble()));
                    break;
                case 6:
                    System.out.println(controlador.procesarDocumento(leerTexto("ID: "), leerTexto("Referencia: ")));
                    break;
                case 7:
                    System.out.println(controlador.consultarAuditoria(leerTexto("ID: ")));
                    break;
                case 0:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }
    }
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }
    private static int leerEntero() {
        String entrada = scanner.hasNextLine() ? scanner.nextLine().trim() : "0";
        try { return Integer.parseInt(entrada); }
        catch (NumberFormatException ex) { return -1; }
    }
    private static double leerDouble() {
        String entrada = leerTexto("Monto: ");
        try { return Double.parseDouble(entrada); }
        catch (NumberFormatException ex) { return Double.NaN; }
    }
}
