import java.util.List;
import java.util.ArrayList;

/**
 * Script Maestro para la ejecución y medición de tiempo de los algoritmos de ordenamiento
 * en MODO UNIPROCESO (Java).
 * Responsabilidad de Orquestación: Daniel (Liderar la fase de pruebas)
 */
public class Main {

    // Información clave para el registro de resultados
    private static final String RUTA_DATASET = "resources/dataset_1M.csv"; //aUQI VA EL DATASET MUCHACHPS
    private static final String NOMBRE_EQUIPO = "Equipo_X_i7_16GB"; // DANIEL debe cambiar esto por el equipo actual YHA QUE NO ME ACUEROD xd

    public static void main(String[] args) {

        // Inicializar el archivo de resultados
        ResultsWriter.writeHeader();
        System.out.println("--- PRUEBAS DE TIEMPO UNIPROCESO INICIADAS ---");

        // 1. CARGA DE DATOS (Responsabilidad de DANIEL - DataReader.java)
        List<Double> datosParaPrueba;
        try {
            // Daniel debe implementar DataReader.leerDatos
            datosParaPrueba = DataReader.leerYLimpiarDataset(RUTA_DATASET);
            System.out.println("Dataset cargado y listo. Tamaño: " + datosParaPrueba.size());

        } catch (Exception e) {
            System.err.println("Error fatal al cargar los datos: " + e.getMessage());
            return;
        }

        // --- PRUEBA 1: ALGORITMO O(n log n) de Ana ---

        // Necesitamos una copia fresca de los datos para cada prueba
        List<Double> datos_nlogn = new ArrayList<>(datosParaPrueba);

        Timer timer1 = new Timer();
        System.out.println("\n[PRUEBA 1] Ejecutando O(n log n)...");

        timer1.start();
        // Ana debe implementar OnLogN_Sort.sort()
        OnLogN_Sort.sort(datos_nlogn);
        timer1.stop();

        double tiempo1 = timer1.getDurationSeconds();
        System.out.printf("Tiempo O(n log n): %.4f segundos\n", tiempo1);

        // 2. Registrar el resultado
        ResultsWriter.writeResult("OnLogN_Sort (Merge/Quick)", "O(n log n)", tiempo1, NOMBRE_EQUIPO);


        // --- PRUEBA 2: ALGORITMO O(n^2) de Daniel ---

        List<Double> datos_n2 = new ArrayList<>(datosParaPrueba); // Otra copia fresca

        Timer timer2 = new Timer();
        System.out.println("\n[PRUEBA 2] Ejecutando O(n^2)...");

        timer2.start();
        // Daniel debe implementar On2_Sort.sort()
        On2_Sort.sort(datos_n2);
        timer2.stop();

        double tiempo2 = timer2.getDurationSeconds();
        System.out.printf("Tiempo O(n^2): %.4f segundos\n", tiempo2);

        // 3. Registrar el resultado
        ResultsWriter.writeResult("On2_Sort (Insertion/Bubble)", "O(n^2)", tiempo2, NOMBRE_EQUIPO);

        System.out.println("\n--- PRUEBAS DE TIEMPO UNIPROCESO FINALIZADAS ---");
    }
}