package proyectoEda;
import java.util.List;
import java.util.ArrayList;
/**
 * 
 * Script Maestro para la ejecución y medición de tiempo de los algoritmos de ordenamiento
 * en MODO UNIPROCESO (Java).
 */
public class Main {

    // Informacion clave para el registro de resultados
    private static final String RUTA_DATASET = "src/datosEda.csv"; 
    private static final String NOMBRE_EQUIPO = "Equipo_Ryzen 7_8845hs_16GB"; 

    public static void main(String[] args) {

       
      
        System.out.println("--- PRUEBAS DE TIEMPO UNIPROCESO INICIADAS ---");

        // 1. CARGA DE DATOS (DataReader.java)
        // modifico la lista de double a NormalizedUtility para que el codigo de MergeSort funcione
        List<NormalizedUtility> datosParaPrueba; 
        try {
            datosParaPrueba = DataReader.leerYLimpiarDataset(RUTA_DATASET);
            System.out.println("Dataset cargado y listo. Tamaño: " + datosParaPrueba.size());
        } catch (Exception e) {
            System.err.println("Error fatal al cargar los datos: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // --- PRUEBA 1: ALGORITMO O(n log n) 

        // Necesitamos una copia fresca de los datos para cada prueba
        List<NormalizedUtility> datos_mergesort = new ArrayList<>(datosParaPrueba); // <-- ¡IDIOMA CORRECTO!

        Timer timer1 = new Timer();
        System.out.println("\n[PRUEBA 1] Ejecutando O(n log n) Mergesort ..."); 

        timer1.start();
      
        OnLogN_Sort.sort(datos_mergesort); //  Llama al Mergesort 
        timer1.stop();

        double tiempo1 = timer1.getDurationSeconds();
        System.out.printf("Tiempo O(n log n) [Mergesort]: %.4f segundos\n", tiempo1);
       
        
        

        // --- PRUEBA 2: ALGORITMO O(nlog n ) Insertion

        List<NormalizedUtility> datos_quicksort = new ArrayList<>(datosParaPrueba); 

        Timer timer2 = new Timer();
        System.out.println("\n[PRUEBA 2] Ejecutando InsertionSort O(n2)..."); 

        timer2.start();
        
        
        On2Insertion_sort.sort(datos_quicksort); 
        
        timer2.stop();

        double tiempo2 = timer2.getDurationSeconds();
        System.out.printf("Tiempo O(n2) [InsertionSort]: %.4f segundos\n", tiempo2);
        
        // Registra tu resultado
       

        System.out.println("\n--- PRUEBAS DE TIEMPO UNIPROCESO FINALIZADAS ---");
    }
}
