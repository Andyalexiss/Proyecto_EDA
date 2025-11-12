package proyectoEda;
import java.util.List;
import java.util.ArrayList;
/**
 * Script Maestro para la ejecución y medición de tiempo de los algoritmos de ordenamiento
 * en MODO UNIPROCESO (Java).
 * Responsabilidad de Orquestación: Daniel (Liderar la fase de pruebas)
 */
public class Main {

    // Información clave para el registro de resultados
    private static final String RUTA_DATASET = "src/datosEda.csv"; //aUQI VA EL DATASET MUCHACHPS
    private static final String NOMBRE_EQUIPO = "Equipo_Ryzen 8_8800hs_16GB"; // DANIEL debe cambiar esto por el equipo actual YHA QUE NO ME ACUEROD xd

    public static void main(String[] args) {

        // Inicializar el archivo de resultados
        ResultsWriter.writeHeader();
        System.out.println("--- PRUEBAS DE TIEMPO UNIPROCESO INICIADAS ---");

        // 1. CARGA DE DATOS (Responsabilidad de DANIEL - DataReader.java)
        // modifico la lista de double a NormalizedUtility para que el codigo de Ana funcione
        List<NormalizedUtility> datosParaPrueba; 
        try {
            datosParaPrueba = DataReader.leerYLimpiarDataset(RUTA_DATASET);
            System.out.println("Dataset cargado y listo. Tamaño: " + datosParaPrueba.size());
        } catch (Exception e) {
            System.err.println("Error fatal al cargar los datos: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // --- PRUEBA 1: ALGORITMO O(n log n) de Ana ---

        // Necesitamos una copia fresca de los datos para cada prueba
        List<NormalizedUtility> datos_mergesort = new ArrayList<>(datosParaPrueba); // <-- ¡IDIOMA CORRECTO!

        Timer timer1 = new Timer();
        System.out.println("\n[PRUEBA 1] Ejecutando O(n log n) de Ana (Mergesort)..."); 

        timer1.start();
      
        OnLogN_Sort.sort(datos_mergesort); //  Llama al Mergesort de Ana
        timer1.stop();

        double tiempo1 = timer1.getDurationSeconds();
        System.out.printf("Tiempo O(n log n) [Mergesort]: %.4f segundos\n", tiempo1);
        ResultsWriter.writeResult("OnLogN_Sort (Mergesort)", "O(n log n)", tiempo1, NOMBRE_EQUIPO);
        
        

        // --- PRUEBA 2: ALGORITMO O(nlog n ) Quicksort de Daniel ---

        List<NormalizedUtility> datos_quicksort = new ArrayList<>(datosParaPrueba); // <-- ¡IDIOMA CORRECTO!

        Timer timer2 = new Timer();
        System.out.println("\n[PRUEBA 2] Ejecutando tu Quicksort O(n log n)..."); 

        timer2.start();
        
        
        On_logn_Quicksort.sort(datos_quicksort); 
        
        timer2.stop();

        double tiempo2 = timer2.getDurationSeconds();
        System.out.printf("Tiempo O(n log n) [Quicksort]: %.4f segundos\n", tiempo2);
        
        // Registra tu resultado
        ResultsWriter.writeResult("On_logn_Quicksort (Daniel)", "O(n log n)", tiempo2, NOMBRE_EQUIPO);

        System.out.println("\n--- PRUEBAS DE TIEMPO UNIPROCESO FINALIZADAS ---");
    }
}