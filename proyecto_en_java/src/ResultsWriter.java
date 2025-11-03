import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Clase para escribir los resultados de tiempo en un archivo CSV.
 * Implementado por Andy.
 */
public class ResultsWriter {

    private static final String FILE_PATH = "output/tiempos_uniproceso.csv";

    public static void writeHeader() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            pw.println("Algoritmo,Complejidad,Tiempo_Segundos,Equipo_CPU");
        } catch (IOException e) {
            System.err.println("Error al escribir el encabezado: " + e.getMessage());
        }
    }

    /**
     * Escribe una línea de resultado en el archivo CSV.
     * @param algoritmo Nombre del algoritmo (Ej: MergeSort).
     * @param complejidad Complejidad (Ej: O(n log n)).
     * @param tiempoSegundos Tiempo de ejecución en segundos.
     * @param equipoInfo Nombre del equipo donde se ejecutó (Ej: Equipo_A_i7).
     */
    public static void writeResult(String algoritmo, String complejidad, double tiempoSegundos, String equipoInfo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, true))) { // 'true' para append
            String line = String.format("%s,%s,%.6f,%s", algoritmo, complejidad, tiempoSegundos, equipoInfo);
            pw.println(line);
            System.out.println("-> Resultado guardado: " + algoritmo);
        } catch (IOException e) {
            System.err.println("Error al escribir el resultado: " + e.getMessage());
        }
    }
}